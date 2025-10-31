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

    /**
     * 查看当前用户购物车数据
     * @param userId 当前用户id
     * @return 用户购物车数据
     */
    List<ShoppingCart> getByUserId(Long userId);

    /**
     * 清空用户购物车
     * @param userId 用户id
     */
    void deleteByUserId(Long userId);

    /**
     * 删除购物车一个物品
     * @param shoppingCart 购物车对象
     */
    void deleteById(ShoppingCart shoppingCart);

    /**
     * 批量添加购物车
     * @param shoppingCartList 购物车集合
     */
    void insertBatch(List<ShoppingCart> shoppingCartList);
}
