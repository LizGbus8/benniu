package com.lzg.tbook.search.mapper;

import com.lzg.tbook.search.entity.SearchItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 作者：LizG on 2018/8/21 20:27
 * 描述：
 */
@Mapper
public interface SearchItemMapper {

    List<SearchItem> getItemList();

    SearchItem getItemById(String contentId);
}
