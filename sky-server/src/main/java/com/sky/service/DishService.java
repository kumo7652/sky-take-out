package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

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

    /**
     * 删除菜品
     * @param ids 菜品id
     */
    void delete(List<Long> ids);
}
