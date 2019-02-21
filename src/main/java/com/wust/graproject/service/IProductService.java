package com.wust.graproject.service;

import com.github.pagehelper.PageInfo;
import com.wust.graproject.entity.Product;


/**
 * @ClassName IProductService
 * @Description TODO
 * @Author leis
 * @Date 2019/2/20 11:10
 * @Version 1.0
 **/
public interface IProductService {
    /**
     * 查询主页电器列表数据
     *
     * @return
     */
    PageInfo<Product> selectTelevision();

}
