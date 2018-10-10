package com.lzg.tbook.search.service.impl;

import com.lzg.tbook.search.dao.SearchDao;
import com.lzg.tbook.search.entity.SearchItem;
import com.lzg.tbook.search.mapper.SearchItemMapper;
import com.lzg.tbook.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.util.List;

/**
 * 作者：LizG on 2018/10/9 10:45
 * 描述：
 */
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SearchItemMapper searchItemMapper;

    @Autowired
    private SearchDao searchDao;

    @Override
    public List<SearchItem> search(String queryString, int page, int size) throws Exception {
        //创建查询条件对象
        SolrQuery query = new SolrQuery();

        //设置查询条件
        query.setQuery(queryString);

        //设置分页条件
        if (page < 1){ page = 1;}
        query.setStart((page-1)*size);
        if (size < 1){size = 10;}
        query.setRows(size);

        log.info("SolrQuery : {}",query);

        //设置默认搜索域
        query.set("df","product_title");

        //设置高亮

        //调用dao进行查询
        List<SearchItem> search = searchDao.search(query);

        return search;
    }
}
