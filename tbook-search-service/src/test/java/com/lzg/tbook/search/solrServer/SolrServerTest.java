package com.lzg.tbook.search.solrServer;


import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;


/**
 * 作者：LizG on 2018/8/21 21:47
 * 描述：
 */
public class SolrServerTest {

    @Test
    public void testAddDocument() throws Exception {
        /** 创建一个solr对象，创建一个HttpSolrServer对象 */
        /** 需要指定solr服务的url */
        HttpSolrClient client = new HttpSolrClient("http://120.79.254.32:8080/solr/collection1");

        /** 创建一个文档对象SolrInputDocument */
        SolrInputDocument document = new SolrInputDocument();

        /** 向文档中添加域，必须有id域，域的名称必须在schema.xml中定义 */
        document.addField("id", "test001");
        document.addField("product_title", "test001");
        /** 把文档对象写入索引库 */
        client.add(document);
        /** 提交 */
        client.commit();
    }

    public void testDocumentById() throws IOException, SolrServerException {
        HttpSolrClient client = new HttpSolrClient("http://120.79.254.32:8080/solr/collection1");
        client.deleteById("test01");
        client.commit();
    }

    public void testDocumentByQuery() throws IOException, SolrServerException {
        HttpSolrClient client = new HttpSolrClient("http://120.79.254.32:8080/solr/collection1");
        client.deleteByQuery("id:123");
        client.deleteByQuery("product_title:123");
        client.commit();
    }
}
