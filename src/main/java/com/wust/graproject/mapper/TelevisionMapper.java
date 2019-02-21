package com.wust.graproject.mapper;

import com.wust.graproject.entity.Product;
import com.wust.graproject.entity.Television;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description TODO
 * @Author leis
 * @Date 2019/2/21 11:06
 **/
@Mapper
@Repository
public interface TelevisionMapper {
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
    int insert(Television record);

    /**
     * 存在则插入数据
     *
     * @param record
     * @return
     */
    int insertSelective(Television record);

    /**
     * 通过主键查询
     *
     * @param id
     * @return
     */
    Television selectByPrimaryKey(Integer id);

    /**
     * 存在则更新数据
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Television record);

    /**
     * 根据主键更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(Television record);

    /**
     * 根据销量查询
     *
     * @return
     */
    List<Product> selectBySold();
}