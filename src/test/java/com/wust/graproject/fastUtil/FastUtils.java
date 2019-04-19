package com.wust.graproject.fastUtil;

import com.wust.graproject.entity.Mobile;
import com.wust.graproject.entity.Product;
import com.wust.graproject.mapper.MobileMapper;
import com.wust.graproject.util.FastDfsUtil;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FastUtils {

    @Autowired
    private MobileMapper mobileMapper;


    private static final String prefix = "E:\\share\\img\\";

    @Test
    public void upload() throws IOException, MyException {
        List<Product> productList = mobileMapper.selectCom();
        for (Product product : productList) {
            if (StringUtils.isNotEmpty(product.getName())) {
//                String mainImage = product.getMainImage();
//                mainImage = mainImage.replace("E:\\share\\img\\", "");
//                mainImage = prifex + mainImage;
//                product.setMainImage(mainImage);
//                mobileMapper.updateByPrimaryKeySelective(product);
//                String subImage = product.getSubImages();
//                String[] str = subImage.split(",");
//                String res = "";
//                for (int i = 0; i < str.length; i++) {
//                    str[i] = prefix + str[i];
//                    res = res + str[i] + ",";
//                }
//                product.setSubImages(res);
//                mobileMapper.updateByPrimaryKeySelective(product);
//                try {
//                    System.out.println(product.getMainImage());
//                    String mainImage = FastDfsUtil.upload(product.getMainImage(), "jpg");
//                    mainImage = mainImage.replace("M00", "");
//                    product.setMainImage(mainImage);
//                    mobileMapper.updateByPrimaryKeySelective(product);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (MyException e) {
//                    e.printStackTrace();
//                }
                String subImage = product.getSubImages();
                String[] str = subImage.split(",");
                String res = "";
                int count = 0;
                for (int i = 0; i < str.length; i++) {
                    //String s = FastDfsUtil.upload(str[i], "jpg");
                    count++;
                    String s = str[i].replace("M00", "");
                    res = res + s + ",";
                    if (count >= 5) {
                        break;
                    }
                }
                product.setSubImages(res);
                mobileMapper.updateByPrimaryKeySelective(product);
            }
        }
    }
}
