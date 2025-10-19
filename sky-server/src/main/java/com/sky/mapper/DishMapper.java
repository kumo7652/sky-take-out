package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishMapper {
    /**
     * 新增菜品
     * @param dish 菜品对象
     */
    @AutoFill(OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 通过id查询菜品
     * @param id 菜品id
     * @return Dish
     */
    Dish getById(Long id);

    /**
     * 查询分类下是否有关联的菜品
     * @param categoryId 分类id
     * @return Integer
     */
    Integer countByCategoryId(Long categoryId);

    /**
     * 分页查询菜品
     * @param dishPageQueryDTO 分页查询菜品对象
     * @return Page
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 批量删除菜品
     * @param ids 菜品id
     */
    void deleteBatch(List<Long> ids);

    /**
     * 更新菜品
     * @param dish 更新菜品对象
     */
    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);

    /**
     * 根据分类id查询菜品列表
     * @param categoryId 分类id
     * @return 菜品列表
     */
    List<Dish> listByCategoryId(Long categoryId);
}
