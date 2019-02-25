package com.wust.graproject.mapper;

import com.wust.graproject.entity.Lipstick;
import com.wust.graproject.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author aisino
 */
@Mapper
@Repository
public interface LipstickMapper {
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
    int insert(Lipstick record);
    /**
     * 存在则插入数据
     *
     * @param record
     * @return
     */
    int insertSelective(Lipstick record);

    /**
     * 通过主键查询
     *
     * @param id
     * @return
     */
    Lipstick selectByPrimaryKey(Integer id);

    /**
     * 存在则更新数据
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Lipstick record);

    /**
     * 根据主键更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(Lipstick record);
    /**
     * 根据销量查询
     *
     * @return
     */
    List<Product> selectLipstickBySold();
}