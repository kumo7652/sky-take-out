package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final DishMapper dishMapper;
    private final SetMealMapper setMealMapper;

    /**
     * 新增分类
     * @param categoryDTO 新增分类对象
     */
    @Override
    public void save(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

        // 默认禁用状态
        category.setStatus(StatusConstant.DISABLE);

        categoryMapper.insert(category);
    }

    /**
     * 分页查询分类对象
     * @param categoryPageQueryDTO 查询条件对象
     * @return PageResult
     */
    @Override
    public PageResult<Category> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        Page<Category> p = PageHelper
                .startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize())
                .doSelectPage(() -> categoryMapper.pageQuery(categoryPageQueryDTO));
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    /**
     * 根据类型查询
     * @param type 类型
     * @return List
     */
    @Override
    public List<Category> list(Integer type) {
        return categoryMapper.getByType(type);
    }

    /**
     * 启用禁用分类
     * @param status 状态
     * @param id     分类id
     */
    @Override
    public void switchStatus(Integer status, Long id) {
        Category category = Category.builder()
                .id(id)
                .status(status)
                .build();

        categoryMapper.update(category);
    }

    /**
     * 修改分类
     * @param categoryDTO 更新分类对象
     */
    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

        categoryMapper.update(category);
    }

    /**
     * 删除分类
     * @param id 分类id
     */
    @Override
    public void delete(Long id) {
        Integer count = dishMapper.countByCategoryId(id);

        if(count > 0){
            //当前分类下有菜品，不能删除
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }

        //查询当前分类是否关联了套餐，如果关联了就抛出业务异常
        count = setMealMapper.countByCategoryId(id);
        if(count > 0){
            //当前分类下有菜品，不能删除
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }

        categoryMapper.deleteById(id);
    }
}
