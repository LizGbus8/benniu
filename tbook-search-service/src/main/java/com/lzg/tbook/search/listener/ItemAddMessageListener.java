package com.lzg.tbook.search.listener;

import com.lzg.manager.dto.Message;
import com.lzg.manager.entity.Content;
import com.lzg.tbook.search.entity.SearchItem;
import com.lzg.tbook.search.mapper.SearchItemMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * 作者：LizG on 2018/10/9 14:25
 * 描述：
 */
@Service
@Slf4j
public class ItemAddMessageListener {
    @Autowired
    private SearchItemMapper searchItemMapper;

    @Autowired
    private HttpSolrServer solrServer;

    @RabbitListener(queues = "search.update")
    public void updateIndex(Message message) {
        try {
            String contentId = (String) message.getData();
            log.info("contentId is {}",contentId);
            //等待数据持久化到数据库
            Thread.sleep(10000);
            //根据contentId 查找item
            SearchItem searchItem = searchItemMapper.getItemById(contentId);

            log.info("searchItem:{}",searchItem);
            //创建文档对象
            SolrInputDocument document = new SolrInputDocument();

            //向文档对象中添加域
            //2.文档中添加域
            document.setField("id", searchItem.getContentId());
            document.setField("product_title", searchItem.getProductTitle());
            document.setField("product_desc", searchItem.getProductDesc());
            document.setField("product_price", searchItem.getProductPrice());
            document.setField("product_img1", searchItem.getProductImg1());
            document.setField("item_category_name", searchItem.getCategoryName());

            //3.把文档写入索引库

            solrServer.add(document);
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
