package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /**
     * 批量添加菜品风味
     * @param flavors 风味
     */
    void insert(List<DishFlavor> flavors);

    /**
     * 批量删除菜品风味
     * @param dishIds 菜品id
     */
    void deleteBatch(List<Long> dishIds);

    /**
     * 查询菜品风味
     * @param dishId 菜品id
     * @return 菜品风味列表
     */
    List<DishFlavor> getFlavorByDishId(Long dishId);
}
