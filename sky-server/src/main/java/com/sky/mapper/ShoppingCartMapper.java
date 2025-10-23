package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    /**
     * 查询用户购物车数据
     * @param shoppingCart 购物车数据
     * @return 用户购物车数据
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * 更新购物车数据
     * @param shoppingCart 购物车数据
     */
    void updateNumberById(ShoppingCart shoppingCart);

    /**
     * 新增购物车数据
     * @param shoppingCart 购物车数据
     */
    void insert(ShoppingCart shoppingCart);
}
