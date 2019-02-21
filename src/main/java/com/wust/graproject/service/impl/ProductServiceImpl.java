package com.wust.graproject.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wust.graproject.entity.Product;
import com.wust.graproject.mapper.TelevisionMapper;
import com.wust.graproject.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public PageInfo<Product> selectTelevision() {
        PageHelper.startPage(1, 10);
        List<Product> products = televisionMapper.selectBySold();
        PageInfo<Product> pageInfo = new PageInfo<>(products);
        return pageInfo;
    }


}
