package com.wust.graproject.mapper;

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
public interface OrderItemMapper {
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
    int insert(OrderItem record);

    /**
     * 存在则插入数据
     *
     * @param record
     * @return
     */
    int insertSelective(OrderItem record);

    /**
     * 通过主键查询
     *
     * @param id
     * @return
     */
    OrderItem selectByPrimaryKey(Integer id);

    /**
     * 存在则更新数据
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(OrderItem record);

    /**
     * 根据主键更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(OrderItem record);

    /**
     * 批量插入
     *
     * @param orderItemList
     */
    void bulkInsert(@Param("orderItemList") List<OrderItem> orderItemList);

    /**
     * 通过订单号查询
     *
     * @param orderNo
     * @return
     */
    List<OrderItem> selectByOrderNo(Long orderNo);

    /**
     * 获取订单条目
     *
     * @param orderNo
     * @param userId
     * @return
     */
    List<OrderItem> selectByOrderNoAndUserId(@Param("orderNo") Long orderNo, @Param("userId") Integer userId);
}