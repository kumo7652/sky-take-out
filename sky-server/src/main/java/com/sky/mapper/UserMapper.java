package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    /**
     * 根据openid查询用户
     * @param openid 用户openid
     * @return 已存储的用户信息
     */
    User getByOpenId(String openid);

    /**
     * 插入用户
     * @param user 用户信息
     */
    void insert(User user);

    /**
     * 根据用户id查询用户
     * @param id 用户id
     * @return 用户信息
     */
    User getById(Long id);
}
