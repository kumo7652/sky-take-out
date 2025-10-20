package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.SetMeal;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SetMealMapper {
    /**
     * 查询分类下是否有关联的菜品
     * @param categoryId 分类id
     * @return Integer
     */
    Integer countByCategoryId(Long categoryId);

    /**
     * 新增套餐
     * @param setMeal 新增套餐对象
     */
    @AutoFill(OperationType.INSERT)
    void insert(SetMeal setMeal);
}
