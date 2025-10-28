package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    /**
     * 添加购物车
     * @param shoppingCartDTO 购物车对象
     */
    void add(ShoppingCartDTO shoppingCartDTO);

    /**
     * 查看购物车
     * @param currentId 当前用户id
     * @return 当前用户购物车列表
     */
    List<ShoppingCart> list(Long currentId);

    /**
     * 删除购物车内商品
     * @param shoppingCartDTO 购物车对象
     */
    void sub(ShoppingCartDTO shoppingCartDTO);

    /**
     * 清空购物车
     * @param currentId 当前用户id
     */
    void clean(Long currentId);
}
