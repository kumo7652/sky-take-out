package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper {
    /**
     * 查询分类下是否有关联的菜品
     * @param categoryId 分类id
     * @return Integer
     */
    Integer countByCategoryId(Long categoryId);
}
