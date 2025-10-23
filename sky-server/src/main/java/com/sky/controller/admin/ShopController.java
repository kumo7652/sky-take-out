package com.sky.controller.admin;

import com.sky.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/shop")
@RestController("adminShopController")
public class ShopController {
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置店铺状态
     * @param status 状态
     * @return Result
     */
    @PutMapping("/{status}")
    public Result setStatus(@PathVariable Integer status) {
        log.info("设置店铺的营业状态：{}", status == 1 ? "营业中" : "打烊中");
        redisTemplate.opsForValue().set("SHOP_STATUS", status);

        return Result.success();
    }

    /**
     * 查询店铺状态
     * @return Result
     */
    @GetMapping("/status")
    public Result getStatus() {
        log.info("管理端查询店铺状态");
        Integer status = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");

        return Result.success(status);
    }
}
