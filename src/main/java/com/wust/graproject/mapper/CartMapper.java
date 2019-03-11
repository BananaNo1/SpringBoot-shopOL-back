package com.wust.graproject.mapper;

import com.wust.graproject.entity.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author ManMan
 */
@Mapper
@Repository
public interface CartMapper {
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
    int insert(Cart record);
    /**
     * 存在则插入数据
     *
     * @param record
     * @return
     */
    int insertSelective(Cart record);
    /**
     * 通过主键查询
     *
     * @param id
     * @return
     */
    Cart selectByPrimaryKey(Integer id);
    /**
     * 存在则更新数据
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Cart record);
    /**
     * 根据主键更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(Cart record);
}