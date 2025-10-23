package com.sky.controller.user;

import com.sky.dto.UserLoginDTO;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.vo.UserLoginVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/user")
public class UserController {
    private final UserService userService;

    /**
     * 微信登录
     * @param userLoginDTO 用户登录信息
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDTO  userLoginDTO) {
        log.info("用户端登录：{}", userLoginDTO);
        // 微信登录
        UserLoginVO userLoginVO = userService.wechatLogin(userLoginDTO);

        return Result.success(userLoginVO);
    }
}
