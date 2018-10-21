package com.lzg.common.utlis;

import lombok.extern.slf4j.Slf4j;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 作者：LizG on 2018/7/30 20:45
 * 描述：图片服务器客户工具
 */
@Slf4j
@Component
public class FastDFSClient {
    private TrackerClient trackerClient = null;
    private TrackerServer trackerServer = null;
    private StorageServer storageServer = null;
    private StorageClient1 storageClient = null;

//    private static String CONFIG1 = null;
//    private static String CONFIG2 = null;
//    static {
//        try {
//          CONFIG1 = new ClassPathResource("fdfs_client.conf").getFile().getAbsolutePath();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public FastDFSClient() throws Exception {
        ClientGlobal.initByTrackers("120.79.254.32:22122");
        trackerClient = new TrackerClient();
        trackerServer = trackerClient.getConnection();
        storageServer = null;
        storageClient = new StorageClient1(trackerServer, storageServer);
    }

    public static void main(String[] args) throws Exception {
        FastDFSClient fastDFSClient = new FastDFSClient();
        String s = fastDFSClient.uploadFile("C:\\Users\\LizG\\Desktop\\test\\23259731-1_w_1.jpg");
        System.out.println(s);
        FileInfo first = fastDFSClient.getFile("first", "M00/00/00/rBKBJ1uwtYOAMhU8AABQcwPiCdA183.jpg");
        System.out.println(first);
    }

    public void getConnect(){
        getFile("first","M00/00/00/rBKBJ1uwtYOAMhU8AABQcwPiCdA183.jpg");
    }

    /**
     * 上传文件方法
     *
     * @param fileName 文件全路径
     * @param extName  文件扩展名，不包含（.）
     * @param metas    文件扩展信息
     * @return
     * @throws Exception
     */
    public String uploadFile(String fileName, String extName, NameValuePair[] metas) throws Exception {
        String result = storageClient.upload_file1(fileName, extName, metas);
        return result;
    }

    public String uploadFile(String fileName) throws Exception {
        return uploadFile(fileName, null, null);
    }

    public String uploadFile(String fileName, String extName) throws Exception {
        return uploadFile(fileName, extName, null);
    }

    /**
     * 上传文件方法
     *
     * @param fileContent 文件的内容，字节数组
     * @param extName     文件扩展名
     * @param metas       文件扩展信息
     * @return
     * @throws Exception
     */
    public String uploadFile(byte[] fileContent, String extName, NameValuePair[] metas) throws Exception {

        String result = storageClient.upload_file1(fileContent, extName, metas);
        return result;
    }

    public String uploadFile(byte[] fileContent) throws Exception {
        return uploadFile(fileContent, null, null);
    }

    public String uploadFile(byte[] fileContent, String extName) throws Exception {
        return uploadFile(fileContent, extName, null);
    }

    public FileInfo getFile(String groupName, String remoteFileName) {
        try {
            return storageClient.get_file_info(groupName, remoteFileName);
        } catch (IOException e) {
            log.error("IO Exception: Get File from Fast DFS failed", e);
        } catch (Exception e) {
            log.error("Non IO Exception: Get File from Fast DFS failed", e);
        }
        return null;
    }
}
