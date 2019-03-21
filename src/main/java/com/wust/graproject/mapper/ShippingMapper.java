package com.wust.graproject.mapper;

import com.wust.graproject.entity.Shipping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ManMan
 */
@Mapper
@Repository
public interface ShippingMapper {
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
    int insert(Shipping record);

    /**
     * 存在则插入数据
     *
     * @param record
     * @return
     */
    int insertSelective(Shipping record);

    /**
     * 通过主键查询
     *
     * @param id
     * @return
     */
    Shipping selectByPrimaryKey(Integer id);

    /**
     * 存在则更新数据
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Shipping record);

    /**
     * 根据主键更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(Shipping record);

    /**
     * 根据用户id选择购物车列表
     *
     * @param userId
     * @return
     */
    List<Shipping> selectByUserId(Integer userId);

    /**
     * 根据用户id和地址id选取地址
     *
     * @param userId
     * @param shippingId
     * @return
     */
    Shipping selectByUserIdAndShippingId(@Param("userId") Integer userId, @Param("shippingId") Integer shippingId);

    /**
     * 更新地址
     *
     * @param shipping
     * @return
     */
    int updateByShipping(Shipping shipping);

    /**
     * 删除地址
     * @param shippingId
     * @param userId
     * @return
     */
    int deleteByShippingIdAndUserId(@Param("shippingId") Integer shippingId, @Param("userId") Integer userId);
}