package com.wust.graproject.schedule;

import com.github.pagehelper.PageInfo;
import com.wust.graproject.entity.Product;
import com.wust.graproject.mapper.TelevisionMapper;
import com.wust.graproject.service.IBookService;
import com.wust.graproject.service.IProductService;
import com.wust.graproject.util.RedisPrefixKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName ScheduleIndex
 * @Description TODO
 * @Author leis
 * @Date 2019/2/21 14:25
 * @Version 1.0
 **/
@Component
@EnableScheduling
public class ScheduleIndex {

    @Autowired
    private IProductService productService;

    @Autowired
    private IBookService bookService;

    @Resource
    private RedisTemplate redisTemplate;

    @Scheduled(cron = "0 0 2 * * ?")
    @Async
    public void selectTelevision() {
        PageInfo<Product> pageInfo = productService.selectTelevision();
        redisTemplate.opsForValue().set(RedisPrefixKeyUtil.INDEX_TV, pageInfo);
    }

    @Scheduled(cron = "0 0 2 * * ?")
    @Async
    public void selectBook() {
        PageInfo<Product> pageInfo = bookService.selectBook();
        redisTemplate.opsForValue().set(RedisPrefixKeyUtil.INDEX_BOOK, pageInfo);
    }

    public void selectLipstick() {

    }
}
