package com.wust.graproject.mapper;

import com.wust.graproject.entity.Order;
import com.wust.graproject.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * 查询订单列表
     *
     * @param userId
     * @return
     */
    List<Order> selectByUserId(Integer userId);

    /**
     * 获取订单详情
     *
     * @param userId
     * @param orderNo
     * @return
     */
    Order selectByUserIdAndOrderNo(@Param("userId") Integer userId, @Param("orderNo") Long orderNo);

    /**
     * 根据订单号查询订单
     *
     * @param orderNo
     * @return
     */
    Order selectByOrderNo(Long orderNo);
}