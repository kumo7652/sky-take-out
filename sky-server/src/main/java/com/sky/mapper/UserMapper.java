package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

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

    /**
     * 查询当前用户总数
     * @param endTime 截止日期
     * @return 用户总数
     */
    Integer getTotalUserAmountSoFar(LocalDateTime endTime);

    /**
     * 查询当天新增用户数
     * @param beginTime 开始时间
     * @param endTime 截止时间
     * @return 新增用户数
     */
    Integer getTodayNewUserAmount(LocalDateTime beginTime, LocalDateTime endTime);
}
