package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

public interface DishService {
    /**
     * 新增菜品
     * @param dishDTO 新增菜品对象
     */
    void saveDishAndFlavors(DishDTO dishDTO);

    /**
     * 分页查询菜品
     * @param dishPageQueryDTO 分页查询菜品对象
     */
    PageResult<DishVO> page(DishPageQueryDTO dishPageQueryDTO);
}
