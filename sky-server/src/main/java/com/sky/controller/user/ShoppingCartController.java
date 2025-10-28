package com.sky.controller.user;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user/shoppingCart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     */
    @PostMapping("/add")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("用户{}添加购物车：{}", BaseContext.getCurrentId(), shoppingCartDTO);
        shoppingCartService.add(shoppingCartDTO);

        return Result.success();
    }

    /**
     * 删除购物车内容
     */
    @PostMapping("/sub")
    public Result sub(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("用户{}删除购物车商品：{}", BaseContext.getCurrentId(), shoppingCartDTO);
        shoppingCartService.sub(shoppingCartDTO);

        return Result.success();
    }

    /**
     * 查看购物车
     */
    @GetMapping("/list")
    public Result list() {
        log.info("用户{}查看购物车", BaseContext.getCurrentId());
        List<ShoppingCart> shoppingCartList = shoppingCartService.list(BaseContext.getCurrentId());

        return Result.success(shoppingCartList);
    }

    /**
     * 清空购物车
     */
    @DeleteMapping("/clean")
    public Result clean() {
        log.info("用户{}清空购物车", BaseContext.getCurrentId());
        shoppingCartService.clean(BaseContext.getCurrentId());

        return Result.success();
    }
}
