package com.lzg.tbook.search.service.impl;

import com.lzg.common.VO.ResultVO;
import com.lzg.common.enums.ResultEnum;
import com.lzg.common.utlis.ResultVOUtil;
import com.lzg.tbook.search.entity.SearchItem;
import com.lzg.tbook.search.mapper.SearchItemMapper;
import com.lzg.tbook.search.service.SearchItemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 作者：LizG on 2018/10/9 14:12
 * 描述：
 */
@Service
@Slf4j
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private SearchItemMapper searchItemMapper;

    @Autowired
    private HttpSolrServer solrServer;

    @Override
    public ResultVO importItems2Index() throws Exception {
        try {
            List<SearchItem> itemList = searchItemMapper.getItemList();
            log.info("itemList is {}",itemList);
            for (SearchItem searchItem : itemList){
                log.info("searchItem is {}",searchItem);
                //1.创建文档对象
                SolrInputDocument document = new SolrInputDocument();

                //2.文档中添加域
                document.setField("id",searchItem.getContentId());
                document.setField("product_title",searchItem.getProductTitle());
                document.setField("product_desc",searchItem.getProductDesc());
                document.setField("product_price",searchItem.getProductPrice().toString());
                document.setField("product_img1",searchItem.getProductImg1());
                document.setField("item_category_name",searchItem.getCategoryName());

                //3.把文档写入索引库
                solrServer.add(document);
            }

            //4.提交
            solrServer.commit();
        }catch (Exception e){
            e.printStackTrace();
            return ResultVOUtil.success(ResultEnum.INDEX_IMPORT_ERROT);
        }
        return ResultVOUtil.success();
    }
}
