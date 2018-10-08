package com.lzg.tbook.content.web.converter;

import com.lzg.manager.entity.Content;
import com.lzg.tbook.content.web.dto.HotProductDTO;
import org.springframework.beans.BeanUtils;

/**
 * 作者：LizG on 2018/9/30 10:20
 * 描述：
 */
public class Content2ProductDTOConverter {
    public static HotProductDTO content2ProductDTO(Content content){
        HotProductDTO productDTO = new HotProductDTO();
        BeanUtils.copyProperties(content,productDTO);
        return productDTO;
    }
}
