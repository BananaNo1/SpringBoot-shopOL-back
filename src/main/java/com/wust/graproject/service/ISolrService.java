package com.wust.graproject.service;

import com.wust.graproject.entity.Product;

import java.util.List;

/**
 * @author leis
 * @Descirption
 * @date 2019/4/5 21:10
 */


public interface ISolrService {
    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    Product searchById(Integer id);

    /**
     * 通过名称 全文搜索
     *
     * @param name
     * @param size
     * @param rows
     * @return
     */
    List<Product> searchByName(String name, Integer size, Integer rows);

    /**
     * 分类查询
     * @param categoryId
     * @return
     */
    List<Product> searchByCategoryId(String categoryId,Integer currentPage, Integer rows);

    /**
     * 添加
     * 修改 当id存在时则是修改
     * @param product
     * @return
     */
    Boolean add(Product product);
}
