package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper {
    /**
     * 新增菜品
     * @param dish 菜品对象
     */
    @AutoFill(OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 查询分类下是否有关联的菜品
     * @param categoryId 分类id
     * @return Integer
     */
    Integer countByCategoryId(Long categoryId);
}
