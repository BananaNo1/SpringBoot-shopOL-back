package com.wust.graproject.util;

import lombok.extern.slf4j.Slf4j;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

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
            String filePath = new ClassPathResource("fdfs_client.conf").getFile().getAbsolutePath();
            ;
            ClientGlobal.init(filePath);
            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            storageServer = trackerClient.getStoreStorage(trackerServer);
            storageClient = new StorageClient(trackerServer, storageServer);
        } catch (Exception e) {
            log.error("FastDFS Client Init Fail!", e);
        }
    }

    public static String upload(String filePath, String type) throws IOException, MyException {
        String[] upload_file = storageClient.upload_file(filePath, type, null);
        return upload_file == null ? null : upload_file[1];
    }

    public static boolean delete(String fileName) throws IOException, MyException {
        int m00 = storageClient.delete_file("group1", "M00/" + fileName);
        return m00 > 0 ? true : false;
    }
}
