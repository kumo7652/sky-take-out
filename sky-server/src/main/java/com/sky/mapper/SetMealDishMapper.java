package com.sky.mapper;

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
}
