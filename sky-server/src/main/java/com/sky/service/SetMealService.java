package com.sky.service;

import com.sky.dto.SetMealDTO;

public interface SetMealService {
    /**
     * 新增菜品信息
     * @param setMealDTO 新增菜品信息对象
     */
    void save(SetMealDTO setMealDTO);
}
