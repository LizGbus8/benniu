package com.lzg.manager.controller;

import com.lzg.common.VO.ResultVO;
import com.lzg.common.utlis.ResultVOUtil;
import com.lzg.manager.entity.Category;
import com.lzg.manager.service.CategoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 作者：LizG on 2018/10/4 19:03
 * 描述：
 */
@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/all")
    public ResultVO all(){
        List<Category> all = categoryService.findAll();
        log.info("categorylist is {}",all);
        return ResultVOUtil.success(all);
    }
}
