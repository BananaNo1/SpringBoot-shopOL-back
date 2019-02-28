package com.wust.graproject.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wust.graproject.entity.Product;
import com.wust.graproject.global.ResultDataDto;
import com.wust.graproject.mapper.BookMapper;
import com.wust.graproject.mapper.LipstickMapper;
import com.wust.graproject.mapper.TelevisionMapper;
import com.wust.graproject.repository.ProductEsRepository;
import com.wust.graproject.service.IProductService;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName ProduceServiceImpl
 * @Description TODO
 * @Author leis
 * @Date 2019/2/20 11:11
 * @Version 1.0
 **/
@Service("productService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private TelevisionMapper televisionMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private LipstickMapper lipstickMapper;

    @Autowired
    private ProductEsRepository productEsRepository;

    @Override
    public PageInfo<Product> selectTelevision() {
        PageHelper.startPage(1, 8);
        List<Product> products = televisionMapper.selectBySold();
        PageInfo<Product> pageInfo = new PageInfo<>(products);
        return pageInfo;
    }

    @Override
    public PageInfo<Product> selectBook() {
        PageHelper.startPage(1, 8);
        List<Product> products = bookMapper.selectBookBySold();
        PageInfo<Product> pageInfo = new PageInfo<>(products);
        return pageInfo;
    }

    @Override
    public PageInfo<Product> selectLipstick() {
        PageHelper.startPage(1, 8);
        List<Product> products = lipstickMapper.selectLipstickBySold();
        PageInfo<Product> pageInfo = new PageInfo<>(products);
        return pageInfo;
    }

    @Override
    public ResultDataDto search(String keyword) {
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("name", keyword);
        Iterable<Product> search = productEsRepository.search(matchQueryBuilder);
        Iterator<Product> iterator = search.iterator();
        List<Product> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return ResultDataDto.operationSuccess().setData(list);
    }

    @Override
    public ResultDataDto getList(Integer categoryId) {

        return null;
    }

    @Override
    public ResultDataDto detail(Integer productId) {
        if (productId <= 0) {
            return ResultDataDto.operationErrorByMessage("参数错误");
        }
        Product product = productEsRepository.findById(productId).get();
        return ResultDataDto.operationSuccess().setData(product);
    }
}
