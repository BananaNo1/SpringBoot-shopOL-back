package com.wust.graproject.junit;

import com.wust.graproject.util.FastDfsUtil;
import org.csource.common.MyException;
import org.junit.Test;

import java.io.IOException;

/**
 * @author leis
 * @Descirption
 * @date 2019/3/31 16:12
 */

public class fastdfs {

    @Test
    public void upload() {
        try {
            String upload = FastDfsUtil.upload("E:/share/img/dh_pic.jpg", "jpg");
            System.out.println(upload);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void delete() {
        try {
            boolean delete = FastDfsUtil.delete("/00/11/rBKfmFygdwiAQnHlAAAsRBkI2Pk102.png");
            System.out.println(delete);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
}
