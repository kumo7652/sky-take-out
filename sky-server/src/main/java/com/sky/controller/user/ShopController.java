package com.sky.controller.user;

import com.sky.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user/shop")
@RestController("userShopController")
public class ShopController {
    private final RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/status")
    public Result getStatus() {
        log.info("用户端查询店铺状态");
        Integer status = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");

        return Result.success(status);
    }
}
