package com.sky.service;

import com.sky.dto.DishDTO;

public interface DishService {
    /**
     * 新增菜品
     * @param dishDTO 新增菜品对象
     */
    void saveDishAndFlavors(DishDTO dishDTO);
}
