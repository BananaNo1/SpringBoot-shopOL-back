package com.wust.graproject.repository;

import com.github.pagehelper.PageInfo;
import com.wust.graproject.entity.Product;
import com.wust.graproject.mapper.*;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName Es
 * @Description TODO
 * @Author leis
 * @Date 2019/2/26 15:46
 * @Version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class Es {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private TelevisionMapper televisionMapper;

    @Autowired
    private LipstickMapper lipstickMapper;

    @Autowired
    private MobileMapper mobileMapper;

    @Autowired
    private ClothesMapper clothesMapper;
    @Autowired
    private ProductEsRepository productEsRepository;

    @Test
    public void add() {
//        List<Product> products = bookMapper.selectBookBySold();
//        productEsRepository.saveAll(products);
//        Iterable<Product> all = productEsRepository.findAll();
//        List<Product> list = new ArrayList<>();
//        all.forEach(product -> {
//            list.add(product);
//        });

//        List<Product> products1 = televisionMapper.selectBySold();
//        productEsRepository.saveAll(products1);
//        List<Product> products2 = lipstickMapper.selectLipstickBySold();
//        productEsRepository.saveAll(products2);
//        List<Product> products3 = mobileMapper.selectBySold();
//        productEsRepository.saveAll(products3);
//        List<Product> products4 = clothesMapper.selectBySold();
//        productEsRepository.saveAll(products4);
        Iterable<Product> all = productEsRepository.findAll();


    }

    @Test
    public void find() {
        Optional<Product> byId = productEsRepository.findById(5);
        Product product = byId.get();

    }

    @Test
    public void search() {
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("name", "手机");
        Iterable<Product> search = productEsRepository.search(matchQueryBuilder,PageRequest.of(1,20));
        PageInfo<Product> pageInfo = new PageInfo<>(((Page<Product>) search).getContent());
        pageInfo.setSize(((Page<Product>) search).getTotalPages());
    }

}
