package com.lzg.tbook.search.config;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 作者：LizG on 2018/10/9 12:09
 * 描述：
 */
@Configuration
public class SolrClientConfig {
    private static final String BASE_URL = "http://120.79.254.32:8080/solr/collection1";
    @Bean
    public HttpSolrServer httpSolrServer(){
        return new HttpSolrServer(BASE_URL);
    }
}
