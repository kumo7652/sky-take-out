package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

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
}
