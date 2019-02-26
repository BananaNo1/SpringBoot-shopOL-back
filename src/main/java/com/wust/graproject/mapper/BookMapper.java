package com.wust.graproject.mapper;

import com.wust.graproject.entity.Book;
import com.wust.graproject.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description TODO
 * @Author leis
 * @Date 2019/2/21 15:00
 **/
@Mapper
@Repository
public interface BookMapper {
    /**
     * 通过主键删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入数据
     *
     * @param record
     * @return
     */
    int insert(Book record);

    /**
     * 存在则插入数据
     *
     * @param record
     * @return
     */
    int insertSelective(Book record);

    /**
     * 通过主键查询
     *
     * @param id
     * @return
     */
    Book selectByPrimaryKey(Integer id);

    /**
     * 存在则更新数据
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Book record);

    /**
     * 根据主键更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(Book record);

    /**
     * 根据销量查询
     *
     * @return
     */
    List<Product> selectBookBySold();

}