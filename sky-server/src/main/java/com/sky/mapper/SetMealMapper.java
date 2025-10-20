package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetMealPageQueryDTO;
import com.sky.entity.SetMeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetMealVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SetMealMapper {
    /**
     * 查询分类下是否有关联的菜品
     * @param categoryId 分类id
     * @return Integer
     */
    Integer countByCategoryId(Long categoryId);

    /**
     * 新增套餐
     * @param setMeal 新增套餐对象
     */
    @AutoFill(OperationType.INSERT)
    void insert(SetMeal setMeal);

    /**
     * 分页查询套餐信息
     * @param setMealPageQueryDTO 分页查询参数
     */
    Page<SetMealVO> pageQuery(SetMealPageQueryDTO setMealPageQueryDTO);

    /**
     * 修改套餐信息
     * @param setMeal 套餐对象
     */
    @AutoFill(OperationType.UPDATE)
    void update(SetMeal setMeal);

    /**
     * 通过id查询套餐信息
     * @param id 套餐id
     * @return 套餐信息
     */
    SetMeal getById(Long id);
}
