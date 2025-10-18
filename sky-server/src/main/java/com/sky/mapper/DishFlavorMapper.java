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
}
