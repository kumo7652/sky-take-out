package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
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

    /**
     * 启售禁售菜品
     * @param status 菜品状态
     * @param id 菜品id
     */
    void switchStatus(Integer status, Long id);

    /**
     * 根据id查询菜品
     * @param id 菜品id
     * @return DishVO
     */
    DishVO getDishVOById(Long id);

    /**
     * 更新菜品信息
     * @param dishDTO 更新菜品信息对象
     */
    void update(DishDTO dishDTO);

    /**
     * 根据分类id查询菜品列表
     * @param categoryId 分类id
     * @return 菜品列表
     */
    List<Dish> listByCategoryId(Long categoryId);
}
