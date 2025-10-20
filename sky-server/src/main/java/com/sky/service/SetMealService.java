package com.sky.service;

import com.sky.dto.SetMealDTO;
import com.sky.dto.SetMealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetMealVO;

public interface SetMealService {
    /**
     * 新增菜品信息
     * @param setMealDTO 新增菜品信息对象
     */
    void save(SetMealDTO setMealDTO);

    /**
     * 分页查询套餐信息
     * @param setMealPageQueryDTO 分页查询餐宿
     * @return PageResult
     */
    PageResult<SetMealVO> page(SetMealPageQueryDTO setMealPageQueryDTO);
}
