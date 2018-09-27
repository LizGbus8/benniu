package com.lzg.tbook.content.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lzg.common.VO.ResultVO;
import com.lzg.common.enums.ResultEnum;
import com.lzg.common.exception.TBookException;
import com.lzg.common.redis.RedisUtil;
import com.lzg.common.utlis.ResultVOUtil;
import com.lzg.manager.entity.Category;
import com.lzg.manager.entity.Content;
import com.lzg.manager.entity.School;
import com.lzg.manager.service.CategoryService;
import com.lzg.manager.service.ContentService;
import com.lzg.manager.service.SchoolService;
import com.lzg.tbook.content.web.form.ContentForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

    @PostMapping(value = "/content")
    public ResultVO publish(@Valid @RequestBody ContentForm contentForm, BindingResult bindingResult) {

        log.info("content is {}", contentForm);
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
        /** 获取openid */
        /** 获取用户信息 */
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        Object user = redisUtil.get(token);
        log.info("user is {}", user);

        /** 商品入库 */
        Content content = new Content();
        BeanUtils.copyProperties(contentForm, content);
        content.setCategoryId(category.getCategoryId());
        Content result = contentService.saveContent(content);

        /** 异步更新redis热门商品队列 */
        try {
            rabbitTemplate.convertAndSend("category",
                    "category.add2hot", category.getCategorySname());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return ResultVOUtil.success(result);
    }

    @GetMapping(value = "/content/hot")
    public ResultVO getHotProduct(@RequestParam String token) {
        //根据token获取用户信息-interest

        //获取interest中的热门商品

        //热门商品随机化

        //返回
        return null;
    }
}
