package com.wust.graproject.service;

import com.github.pagehelper.PageInfo;
import com.wust.graproject.entity.Product;
import com.wust.graproject.global.ResultDataDto;


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

    /**
     * 查询主页列表数据
     *
     * @return
     */
    PageInfo<Product> selectBook();

    /**
     * 查询主页列表数据
     *
     * @return
     */
    PageInfo<Product> selectLipstick();

    ResultDataDto search(String keyword);

    /**
     * 列表页查询
     * @param categoryId
     * @return
     */
    ResultDataDto getList(String categoryId);

    /**
     * 详情页查询
     * @param productId
     * @return
     */
    ResultDataDto detail(Integer productId);
}
