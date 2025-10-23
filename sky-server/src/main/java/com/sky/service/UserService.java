package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.vo.UserLoginVO;

public interface UserService {
    /**
     * 微信登录
     * @param userLoginDTO 微信用户授权码
     * @return 用户信息
     */
    UserLoginVO wechatLogin(UserLoginDTO userLoginDTO);
}
