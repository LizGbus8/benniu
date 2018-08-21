package com.lzg.tbook.search.mapper;

import com.lzg.tbook.search.entity.SearchItem;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 作者：LizG on 2018/8/21 21:27
 * 描述：
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SearchItemMapperTest {

    @Autowired
    SearchItemMapper searchItemMapper;

    @Test
    public void getItemList() {
        List<SearchItem> itemList = searchItemMapper.getItemList();
        System.out.println(itemList);
        Assert.assertNotNull(itemList);
    }
}