package com.lzg.tbook.search.dao;

import com.lzg.tbook.search.entity.SearchItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：LizG on 2018/10/9 12:26
 * 描述：
 */
@Repository
@Slf4j
public class SearchDao {

    @Autowired
    private HttpSolrServer solrServer;

    public List<SearchItem> search(SolrQuery query) throws IOException, SolrServerException {

        log.info("querystr : {}",query.getQuery());


        //根据query对象进行查询
        QueryResponse response = solrServer.query(query);

        //取出查询结构
        SolrDocumentList solrDocumentList = response.getResults();

        List<SearchItem> searchItemList = new ArrayList<>();

        //遍历封装
        for (SolrDocument solrDocument : solrDocumentList){
            SearchItem item = new SearchItem();
            item.setCategoryName((String) solrDocument.get("item_category_name"));
            item.setContentId((String) solrDocument.get("id"));
            item.setProductTitle((String) solrDocument.get("product_title"));
            item.setProductDesc((String) solrDocument.get("product_desc"));
            item.setProductImg1((String) solrDocument.get("product_img1"));
            item.setProductPrice((Long) solrDocument.get("product_price"));

            /* 加入列表 */
            searchItemList.add(item);
        }
        log.info("searchItemList : {}",searchItemList);

        return searchItemList;
    }
}
