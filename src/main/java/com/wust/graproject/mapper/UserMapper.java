package com.wust.graproject.mapper;

import com.wust.graproject.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author aisino
 */
@Mapper
@Repository
public interface UserMapper {

    String TABLE_NAME = "user";

    String SELECT_FIELDS = "id,";

    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入
     *
     * @param record
     * @return
     */
    int insert(User record);

    /**
     * 选择插入
     *
     * @param record
     * @return
     */
    int insertSelective(User record);

    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    User selectByPrimaryKey(Integer id);

    /**
     * 根据主键选择更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 根据主键更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(User record);

    /**
     * 查看用户名
     *
     * @param username
     * @return
     */
    @Select({"select count(id) from ", TABLE_NAME, " where username = #{username}"})
    Integer checkUsername(String username);

    /**
     * 查询用户
     * @param username
     * @return
     */
    User selectByUsername(String username);
}