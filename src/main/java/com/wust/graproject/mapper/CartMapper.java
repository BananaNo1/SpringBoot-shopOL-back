package com.wust.graproject.mapper;

import com.wust.graproject.entity.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * 根据用户id查询购物车
     *
     * @param userId
     * @return
     */
    List<Cart> selectCartByUserId(Integer userId);

    /**
     * 购物车状态
     *
     * @param userId
     * @return
     */
    int selectCartStatusByUserId(Integer userId);

    /**
     * 购物车数量
     *
     * @param userId
     * @return
     */
    int selectCartProductCount(Integer userId);

    /**
     * 查询购物车
     *
     * @param userId
     * @param productId
     * @return
     */
    Cart selectCartByUserIdAndProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    /**
     * 删除购物车
     *
     * @param userId
     * @param productIds
     * @return
     */
    int deleteByUserIdProductIds(@Param("userId") Integer userId, @Param("productIds") List<String> productIds);

    /**
     * 修改状态
     *
     * @param userId
     * @param productIds
     * @param checked
     * @return
     */
    int checkedOrUncheckedProduct(@Param("userId") Integer userId, @Param("productId") Integer productIds,
                                  @Param("checked") Integer checked);

    /**
     * 查询购物车中被选中的商品
     * @param id
     * @return
     */
    List<Cart> selectCheckedCartByUserId(Integer id);
}