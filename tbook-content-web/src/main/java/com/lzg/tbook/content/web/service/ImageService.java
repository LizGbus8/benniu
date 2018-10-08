package com.lzg.tbook.content.web.service;

import com.lzg.common.enums.ResultEnum;
import com.lzg.common.exception.TBookException;
import com.lzg.common.utlis.FastDFSClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * 作者：LizG on 2018/9/30 16:37
 * 描述：
 */
@Service
@Slf4j
public class ImageService {
    @Autowired
    private FastDFSClient dfsClient;

    public String upload(byte[] fileContent){
        String dfsPath = null;
        try {
            dfsPath = dfsClient.uploadFile(fileContent,"png");
        } catch (Exception e) {
            log.error("image upload fail");
            e.printStackTrace();
        }
        return dfsPath;
    }
}
