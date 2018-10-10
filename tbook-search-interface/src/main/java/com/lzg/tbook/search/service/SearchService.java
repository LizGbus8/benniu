package com.lzg.tbook.search.service;

import com.lzg.tbook.search.entity.SearchItem;

import java.util.List;

/**
 * 作者：LizG on 2018/10/9 10:39
 * 描述：
 */
public interface SearchService {
    List<SearchItem> search(String queryString, int page, int size) throws Exception;
}
