package com.wust.graproject.mapper;

import com.wust.graproject.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author ManMan
 */
@Mapper
@Repository
public interface OrderMapper {
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
    int insert(Order record);

    /**
     * 存在则插入数据
     *
     * @param record
     * @return
     */
    int insertSelective(Order record);

    /**
     * 通过主键查询
     *
     * @param id
     * @return
     */
    Order selectByPrimaryKey(Integer id);

    /**
     * 存在则更新数据
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Order record);

    /**
     * 根据主键更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(Order record);
}