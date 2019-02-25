package com.wust.graproject.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wust.graproject.entity.Product;
import com.wust.graproject.mapper.BookMapper;
import com.wust.graproject.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName BookServiceImpl
 * @Description TODO
 * @Author leis
 * @Date 2019/2/21 14:58
 * @Version 1.0
 **/
@Service
public class BookServiceImpl implements IBookService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public PageInfo<Product> selectBook() {
        PageHelper.startPage(1, 8);
        List<Product> products = bookMapper.selectBookBySold();
        PageInfo<Product> pageInfo = new PageInfo<>(products);
        return pageInfo;
    }
}
