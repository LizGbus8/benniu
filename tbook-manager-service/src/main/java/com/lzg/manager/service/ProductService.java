package com.lzg.manager.service;

import com.lzg.common.VO.ProductVO;
import com.lzg.common.enums.ResultEnum;
import com.lzg.common.exception.TBookException;
import com.lzg.common.redis.RedisLock;
import com.lzg.common.utlis.KeyUtil;
import com.lzg.manager.dao.ContentDao;
import com.lzg.manager.dto.ProductDTO;
import com.lzg.manager.entity.Content;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：LizG on 2018/8/3 22:44
 * 描述：
 */
@Service
public class ProductService {

    @Autowired
    private ContentDao contentDao;

    public List<ProductDTO> getProductsByCgr(Integer categoryId,Pageable pageable){
        if (StringUtils.isEmpty(categoryId)){
            throw new TBookException(ResultEnum.CATEGORY_NOT_EXIST);
        }
        Page<Content> contentPage = contentDao.findByCategoryIdOrderByCreateTime(categoryId, pageable);

        List<ProductDTO> list = new ArrayList<>();

        for (Content c : contentPage.getContent()){
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId(c.getContentId());
            BeanUtils.copyProperties(c,productDTO);
            list.add(productDTO);
        }

        return list;
    }

    public List<ProductDTO> getProductsByUserId(String userId,Pageable pageable){
        if (StringUtils.isEmpty(userId)){
            throw new TBookException(ResultEnum.NOT_HAS_LOGIN);
        }
        Page<Content> contentPage = contentDao.findByUserIdOrderByCreateTime(userId,pageable);

        List<ProductDTO> list = new ArrayList<>();

        for (Content c : contentPage.getContent()){
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId(c.getContentId());
            BeanUtils.copyProperties(c,productDTO);
            list.add(productDTO);
        }

        return list;
    }
}
