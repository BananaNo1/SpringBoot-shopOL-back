package com.wust.graproject.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author leis
 * @Descirption
 * @date 2019/3/31 15:46
 */

@Slf4j
public class FastDfsUtil {

    private static TrackerClient trackerClient = null;

    private static TrackerServer trackerServer = null;

    private static StorageServer storageServer = null;
    private static StorageClient storageClient = null;


    static {
        try {
            ClassPathResource resource = new ClassPathResource("fdfs_client.conf");
            String tempPath = System.getProperty("java.io.tmpdir") + System.currentTimeMillis();
            InputStream inputStream = resource.getInputStream();
            /*String filePath = new ClassPathResource("fdfs_client.conf").getFile().getAbsolutePath();*/
            //Resource resource =  ResourceLoader().getResource("classpath:/fdfs_client.conf");
            File f = new File(tempPath);
            IOUtils.copy(resource.getInputStream(), new FileOutputStream(f));
            ClientGlobal.init(f.getAbsolutePath());
            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            storageServer = trackerClient.getStoreStorage(trackerServer);
            storageClient = new StorageClient(trackerServer, storageServer);
        } catch (Exception e) {
            log.error("FastDFS Client Init Fail!", e);
        }
    }

    public static String upload(String filePath, String type) throws IOException, MyException {
        log.info("***************");
        log.info("filePath:" + filePath + "type:" + type);
        String[] upload_file = storageClient.upload_file(filePath, type, null);
        log.info("upload_file" + upload_file);
        log.info("*************");
        return upload_file == null ? null : upload_file[1];
    }

    public static boolean delete(String fileName) throws IOException, MyException {
        int m00 = storageClient.delete_file("group1", "M00/" + fileName);
        return m00 > 0 ? true : false;
    }
}
