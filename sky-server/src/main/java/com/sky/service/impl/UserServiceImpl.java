package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sky.constant.JwtClaimsConstant;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.JwtProperties;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    // 微信服务接口地址
    private static final String WECHAT_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    private final WeChatProperties weChatProperties;
    private final JwtProperties  jwtProperties;
    private final UserMapper userMapper;

    /**
     * 微信登录
     * @param userLoginDTO 微信用户授权码
     * @return 用户信息
     */
    @Override
    public UserLoginVO wechatLogin(UserLoginDTO userLoginDTO) {
        String openId = getOpenId(userLoginDTO.getCode());

        // 校验openId
        if (openId == null || openId.isEmpty()) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        // 校验是否为新用户
        User user = userMapper.getByOpenId(openId);
        if (user == null) {
            // 新用户自动注册
            user = User.builder()
                    .openid(openId)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }

        // 登录成功下发令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());

        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(), // 密钥
                jwtProperties.getAdminTtl(), // 过期时间
                claims);

        // 返回结果
        return UserLoginVO.builder()
                .id(user.getId())
                .openid(openId)
                .token(token)
                .build();
    }

    /**
     * 调用微信接口获取openid
     * @param code 校验码
     * @return openid
     */
    private String getOpenId(String code) {
        // 构建参数列表
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("appid", weChatProperties.getAppid());
        paramMap.put("secret", weChatProperties.getSecret());
        paramMap.put("js_code", code);
        paramMap.put("grant_type", "authorization_code");

        // 调用微信接口，获取openId
        String wxResult = HttpClientUtil.doGet(WECHAT_LOGIN, paramMap);
        JSONObject jsonObject = JSONObject.parseObject(wxResult);
        return jsonObject.getString("openid");
    }
}
