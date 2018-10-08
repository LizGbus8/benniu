package com.lzg.tbook.content.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lzg.common.VO.ResultVO;
import com.lzg.common.enums.ResultEnum;
import com.lzg.common.exception.TBookException;
import com.lzg.common.redis.RedisUtil;
import com.lzg.common.utlis.InterestUtils;
import com.lzg.common.utlis.JsonUtilByGson;
import com.lzg.common.utlis.KeyUtil;
import com.lzg.common.utlis.ResultVOUtil;
import com.lzg.manager.entity.Category;
import com.lzg.manager.entity.Content;
import com.lzg.manager.entity.School;
import com.lzg.manager.entity.User;
import com.lzg.manager.service.CategoryService;
import com.lzg.manager.service.ContentService;
import com.lzg.manager.service.SchoolService;
import com.lzg.tbook.content.web.converter.Content2ProductDTOConverter;
import com.lzg.tbook.content.web.dto.HotContentDTO;
import com.lzg.tbook.content.web.dto.HotProductDTO;
import com.lzg.tbook.content.web.form.ContentForm;
import com.lzg.tbook.content.web.service.ImageService;
import com.lzg.tbook.content.web.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

/**
 * 作者：LizG on 2018/8/19 22:47
 * 描述：
 */
@RestController
@Slf4j
public class ContentController {

    @Reference
    private ContentService contentService;

    @Reference
    private CategoryService categoryService;

    @Reference
    private SchoolService schoolService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ImageService imageService;

    @Autowired
    ProductService productService;

    private static final String IP_ADRESS = "http://120.79.254.32/";

    @PostMapping(value = "/content")
    public ResultVO publish(@Valid @RequestBody ContentForm contentForm, BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            throw new TBookException(ResultEnum.FORM_PARAM_ERROR);
        }

        /** 查找类目是否存在 */
        Category category = categoryService.findCategoryByName(contentForm.getCategoryName());
        if (category == null) {
            throw new TBookException(ResultEnum.CATEGORY_NOT_EXIST);
        }

        /** 查找学校是否存在 */
        School school = schoolService.findSchoolByName(contentForm.getSchoolName());
        if (school == null) {
            throw new TBookException(ResultEnum.SCHOOL_NOT_EXIST);
        }
        log.info("school is {}", school);
        /** 获取openid */
        /** 获取用户信息 */
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        String json = (String) redisUtil.get(token);
        User user = JsonUtilByGson.jsonToBean(json, User.class);
        log.info("user is {}", user);

        Content content = new Content();

        /** 上传图片 */
        if (!StringUtils.isEmpty(contentForm.getProductImg1())) {
            content.setProductImg1(contentForm.getProductImg1());
        }
        if (!StringUtils.isEmpty(contentForm.getProductImg2())) {
            content.setProductImg2(contentForm.getProductImg2());
        }
        if (!StringUtils.isEmpty(contentForm.getProductImg3())) {
            content.setProductImg3(contentForm.getProductImg3());
        }

        /** 商品入库 */
        BeanUtils.copyProperties(contentForm, content);
        String contentId = KeyUtil.getKey();
        content.setContentId(contentId);
        content.setUserId(user.getOpenid());
        content.setSchoolcode(school.getSchoolcode());
        content.setCategoryId(category.getCategoryId());
        content.setContact(contentForm.getContactType() + "-" + contentForm.getContactNum());
        Content result = contentService.saveContent(content);

        log.info("content is {}", content);
        /** 异步更新redis热门商品队列 */
        //content 2 productDTO
        HotProductDTO productDTO = Content2ProductDTOConverter.content2ProductDTO(content);
        log.info("productDTO is {}", productDTO);

        HotContentDTO hotContent = new HotContentDTO(category.getCategorySname(), productDTO, 0.0);
        log.info("hotContent is {}", hotContent);

        try {
            rabbitTemplate.convertAndSend("category",
                    "category.add2hot", hotContent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultVOUtil.success(result);
    }

    @GetMapping(value = "/content/hot")
    public ResultVO getHotProduct() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");

        //根据token获取用户信息-interest
        String json = (String) redisUtil.get(token);
        User user = JsonUtilByGson.jsonToBean(json, User.class);
        Integer interest = user.getInterest();
        log.info("interest is {}", interest);

        //得到interest简称的集合
        ArrayList<String> interests = InterestUtils.getInterests(interest);

        //获取interest中的热门商品
        Set<Object> hotContentSet = new HashSet<>();
        for (String key : interests) {
            hotContentSet.addAll(redisUtil.zRank(key, 0L, 30L));
        }

        //封装product
        ArrayList<HotProductDTO> hots = new ArrayList<>();
        Iterator<Object> iterator = hotContentSet.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            HotProductDTO hotProductDTO = JsonUtilByGson.jsonToBean((String) next, HotProductDTO.class);
            hots.add(hotProductDTO);
        }

        //热门商品随机化
        Collections.shuffle(hots);

        log.info("hots has {}", hots);

        //返回
        return ResultVOUtil.success(hots);
    }

    @PutMapping(value = "/content/{id}")
    public ResultVO read(@PathVariable("id") String contentId) {
        if (StringUtils.isEmpty(contentId)) {
            throw new TBookException(ResultEnum.CONTENT_NOT_EXIST);
        }

        Content findContent = contentService.findContentById(contentId);

        if (findContent == null) {
            throw new TBookException(ResultEnum.CONTENT_NOT_EXIST);
        }
        productService.increaseRead(findContent);

        return ResultVOUtil.success();
    }

    @PostMapping(value = "content/img")
    public String upload(@RequestParam("img") MultipartFile multipartFile) {
        log.info("multipartFile ={}", multipartFile.getName());
        String path = null;
        try {
            path = imageService.upload(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("保存路径={}", path);
        String[] paths = new String[1];
        paths[0] = IP_ADRESS + path;
        return IP_ADRESS + path;
    }

}
