package com.wust.graproject.mapper;

import com.wust.graproject.entity.PayInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author ManMan
 */
@Mapper
@Repository
public interface PayInfoMapper {
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
    int insert(PayInfo record);

    /**
     * 存在则插入数据
     *
     * @param record
     * @return
     */
    int insertSelective(PayInfo record);

    /**
     * 通过主键查询
     *
     * @param id
     * @return
     */
    PayInfo selectByPrimaryKey(Integer id);

    /**
     * 存在则更新数据
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(PayInfo record);

    /**
     * 根据主键更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(PayInfo record);
}