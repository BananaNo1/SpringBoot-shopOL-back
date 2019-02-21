package com.wust.graproject.service;

import com.github.pagehelper.PageInfo;
import com.wust.graproject.entity.Product;

/**
 * @ClassName IBookService
 * @Description TODO
 * @Author leis
 * @Date 2019/2/21 14:54
 * @Version 1.0
 **/
public interface IBookService {

     PageInfo<Product> selectBook();
}
