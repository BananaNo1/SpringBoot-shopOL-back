package com.wust.graproject.mapper;

import com.wust.graproject.entity.Mobile;
import com.wust.graproject.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author aisino
 */
@Mapper
@Repository
public interface MobileMapper {
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
    int insert(Mobile record);

    /**
     * 存在则插入数据
     *
     * @param record
     * @return
     */
    int insertSelective(Mobile record);

    /**
     * 通过主键查询
     *
     * @param id
     * @return
     */
    Mobile selectByPrimaryKey(Integer id);

    /**
     * 存在则更新数据
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Product record);

    /**
     * 根据主键更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(Mobile record);

    /**
     * 根据销量查询
     *
     * @return
     */
    List<Product> selectBySold();

    List<Product> selectCom();
}