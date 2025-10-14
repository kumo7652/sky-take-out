package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    /**
     * 新增分类
     * @param category 分类对象
     */
    void insert(Category category);

    /**
     * 分页查询
     * @param categoryPageQueryDTO 分页对象
     * @return Page
     */
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 根据类型查询
     * @param type 类型
     * @return List
     */
    List<Category> getByType(Integer type);

    /**
     * 修改分类
     * @param category 分类对象
     */
    void update(Category category);
}
