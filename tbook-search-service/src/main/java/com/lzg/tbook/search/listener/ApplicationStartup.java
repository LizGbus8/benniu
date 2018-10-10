package com.lzg.tbook.search.listener;

import com.lzg.common.VO.ResultVO;
import com.lzg.tbook.search.service.SearchItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 作者：LizG on 2018/10/9 16:05
 * 描述：
 */
@Slf4j
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        SearchItemService impotIndex = event.getApplicationContext().getBean(SearchItemService.class);
        ResultVO resultVO = null;
        try {
            resultVO = impotIndex.importItems2Index();
        } catch (Exception e) {
            log.info("索引导入失败");
            e.printStackTrace();
        }
        log.info("result is {}",resultVO);
    }
}
