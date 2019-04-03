package com.wust.graproject.mapper;

import com.github.pagehelper.PageInfo;
import com.wust.graproject.GraprojectApplication;
import com.wust.graproject.entity.Product;
import com.wust.graproject.entity.User;
import com.wust.graproject.service.IBookService;
import com.wust.graproject.service.impl.ProductServiceImpl;
import com.wust.graproject.util.RedisPrefixKeyUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName ProductServiceImplTest
 * @Description TODO
 * @Author leis
 * @Date 2019/2/21 11:39
 * @Version 1.0
 **/
@SpringBootTest(classes = GraprojectApplication.class)
@RunWith(SpringRunner.class)
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    private IBookService bookService;

    @Test
    public void selectTelevision() {
        //    PageInfo<Product> pageInfo = productService.selectTelevision();
        //    List<Product> list = pageInfo.getList();
//        for(Product product :list){
//            String subImages = product.getSubImages();
//            String[] split = subImages.split(",");
//            System.out.println(split.length);
//        }
        PageInfo<Product> pageInfo1 = bookService.selectBook();

    }

    @Test
    public void selectBook() {
        PageInfo<Product> pageInfo = bookService.selectBook();
    }

    @Test
    public void redisKey() {

        User user= (User)redisTemplate.opsForValue().get(RedisPrefixKeyUtil.TOKEN + "9a3c655e7b77438fab76cd03d24a6877");
    }
}
