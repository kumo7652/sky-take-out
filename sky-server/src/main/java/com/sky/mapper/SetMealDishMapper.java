package com.sky.mapper;

import com.sky.entity.SetMealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetMealDishMapper {
    /**
     * 根据菜品id查询关联套餐
     * @param dishId 菜品id
     * @return 套餐id集合
     */
    List<Long> getSetMealByDishId(Long dishId);

    /**
     * 新增套餐菜品关联信息
     * @param setMealDishes 关联信息集合
     */
    void insert(List<SetMealDish> setMealDishes);

    /**
     * 根据套餐id查询关联菜品
     * @param setMealId 套餐id
     * @return 菜品信息
     */
    List<SetMealDish> getBySetMealId(Long setMealId);
}
