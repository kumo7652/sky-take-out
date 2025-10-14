package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {
    /**
     * 新增分类
     * @param categoryDTO 新增分类对象
     */
    void save(CategoryDTO categoryDTO);

    /**
     * 分页查询分类对象
     * @param categoryPageQueryDTO 查询条件对象
     * @return PageResult
     */
    PageResult<Category> page(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 根据分类查询
     * @param type 类型
     * @return List
     */
    List<Category> list(Integer type);

    /**
     * 启用禁用分类
     * @param status 状态
     * @param id 分类id
     */
    void switchStatus(Integer status, Long id);

    /**
     * 修改分类
     * @param categoryDTO 更新分类对象
     */
    void update(CategoryDTO categoryDTO);
}
