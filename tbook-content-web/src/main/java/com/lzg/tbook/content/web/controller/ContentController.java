package com.lzg.tbook.content.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lzg.common.VO.ResultVO;
import com.lzg.common.enums.ResultEnum;
import com.lzg.common.exception.TBookException;
import com.lzg.common.utlis.ResultVOUtil;
import com.lzg.manager.entity.Category;
import com.lzg.manager.entity.Content;
import com.lzg.manager.service.CategoryService;
import com.lzg.manager.service.ContentService;
import com.lzg.tbook.content.web.form.ContentForm;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 作者：LizG on 2018/8/19 22:47
 * 描述：
 */
@RestController
public class ContentController {

    @Reference
    private ContentService contentService;

    @Reference
    private CategoryService categoryService;


    @PostMapping(value = "/content")
    public ResultVO publish(@Valid ContentForm contentForm, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            throw new TBookException(ResultEnum.FORM_PARAM_ERROR);
        }

        /** 查找类目是否存在 */
        Category category = categoryService.findCategoryByType(contentForm.getCategoryType());
        if (category == null){
            throw new TBookException(ResultEnum.CATEGORY_NOT_EXIST);
        }

        Content content = new Content();
        BeanUtils.copyProperties(contentForm,content);
        content.setCategoryId(category.getCategoryId());
        Content result = contentService.saveContent(content);
        return ResultVOUtil.success(result);
    }
}
