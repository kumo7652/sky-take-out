package com.sky.service;

import com.sky.dto.SetMealDTO;
import com.sky.dto.SetMealPageQueryDTO;
import com.sky.entity.SetMeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetMealVO;

import java.util.List;

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

    /**
     * 根据id查询套餐信息
     * @param id 套餐id
     * @return 套餐信息
     */
    SetMealVO getSetMealVOById(Long id);

    /**
     * 启用停用套餐
     * @param status 套餐状态
     * @param id 套餐id
     */
    void switchStatus(Integer status, Long id);

    /**
     * 修改套餐
     * @param setMealDTO 修改套餐对象
     */
    void update(SetMealDTO setMealDTO);

    /**
     * 批量删除套餐
     * @param ids 套餐id
     */
    void delete(List<Long> ids);

    /**
     * 条件查询
     * @param setMeal 套餐对象
     * @return 符合条件套餐
     */
    List<SetMeal> list(SetMeal setMeal);

    /**
     * 根据id查询菜品选项
     * @param id 套餐id
     * @return 菜品选项
     */
    List<DishItemVO> getDishItemById(Long id);
}
