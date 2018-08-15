package com.lzg.common.utlis;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 作者：LizG on 2018/7/30 20:25
 * 描述：
 */
public class FastDFSClientTest {

    @Test
    public void uploadFile() {
        try {
            FastDFSClient fastDFSClient = new FastDFSClient();
            String uploadFile = fastDFSClient.uploadFile("C:\\Users\\LizG\\Pictures\\Typora\\799055-20180123110627022-1271761295.png");
            System.out.println(uploadFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void uploadFile1() {
    }

    @Test
    public void uploadFile2() {
    }

    @Test
    public void uploadFile3() {
    }

    @Test
    public void uploadFile4() {
    }

    @Test
    public void uploadFile5() {
    }
}