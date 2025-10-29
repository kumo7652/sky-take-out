package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.SetMeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartMapper shoppingCartMapper;
    private final DishMapper dishMapper;
    private final SetMealMapper setMealMapper;

    /**
     * 添加购物车
     * @param shoppingCartDTO 购物车对象
     */
    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());

        // 检查当前用户购物车
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        // 数据已经存在
        if (list != null && !list.isEmpty()) {
            shoppingCart.setNumber(list.get(0).getNumber() + 1);
            shoppingCart.setId(list.get(0).getId());
            shoppingCartMapper.updateNumberById(shoppingCart);

            return;
        }

        // 不存在，添加数据
        // 判断添加数据类型
        Long dishId = shoppingCartDTO.getDishId();
        if (dishId != null) {
            Dish dish = dishMapper.getById(dishId);
            shoppingCart.setName(dish.getName());
            shoppingCart.setImage(dish.getImage());
            shoppingCart.setAmount(dish.getPrice());
        } else {
            SetMeal setMeal = setMealMapper.getById(shoppingCartDTO.getSetMealId());
            shoppingCart.setName(setMeal.getName());
            shoppingCart.setImage(setMeal.getImage());
            shoppingCart.setAmount(setMeal.getPrice());
        }

        shoppingCart.setNumber(1);
        shoppingCart.setCreateTime(LocalDateTime.now());
        shoppingCartMapper.insert(shoppingCart);
    }

    /**
     * 查看购物车
     * @param currentId 当前用户id
     * @return 当前用户购物车列表
     */
    @Override
    public List<ShoppingCart> list(Long currentId) {
        return shoppingCartMapper.getByUserId(currentId);
    }

    /**
     * 删除购物车内商品
     * @param shoppingCartDTO 购物车对象
     */
    @Override
    public void sub(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());

        // 检查当前用户购物车
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        if (list != null && !list.isEmpty()) {
            shoppingCart =  list.get(0);
            Integer number = shoppingCart.getNumber();

            if (number > 1) { // 有重复
                shoppingCart.setNumber(number - 1);
                shoppingCartMapper.updateNumberById(shoppingCart);
            } else { // 无重复
                shoppingCartMapper.deleteById(shoppingCart);
            }
        }
    }

    /**
     * 清空购物车
     * @param currentId 当前用户id
     */
    @Override
    public void clean(Long currentId) {
        shoppingCartMapper.deleteByUserId(currentId);
    }
}
