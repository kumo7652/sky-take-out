package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetMealDTO;
import com.sky.dto.SetMealPageQueryDTO;
import com.sky.entity.SetMeal;
import com.sky.entity.SetMealDish;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetMealService;
import com.sky.vo.SetMealVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SetMealServiceImpl implements SetMealService {
    private final SetMealMapper setMealMapper;
    private final SetMealDishMapper setMealDishMapper;

    /**
     * 新增菜品信息
     * @param setMealDTO 新增菜品信息对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SetMealDTO setMealDTO) {
        // 解析数据
        SetMeal setMeal = new SetMeal();
        BeanUtils.copyProperties(setMealDTO, setMeal);

        List<SetMealDish> setMealDishes = setMealDTO.getSetMealDishes();

        // 插入数据
        setMealMapper.insert(setMeal);

        if (setMealDishes != null && !setMealDishes.isEmpty()) {
            setMealDishes.forEach(setMealDish ->
                    setMealDish.setSetMealId(setMeal.getId()));
        }
        setMealDishMapper.insert(setMealDishes);
    }

    /**
     * 分页查询套餐信息
     * @param setMealPageQueryDTO 分页查询餐宿
     * @return PageResult
     */
    @Override
    public PageResult<SetMealVO> page(SetMealPageQueryDTO setMealPageQueryDTO) {
        Page<SetMealVO> p = PageHelper
                .startPage(setMealPageQueryDTO.getPage(), setMealPageQueryDTO.getPageSize())
                .doSelectPage(()-> setMealMapper.pageQuery(setMealPageQueryDTO));

        return new PageResult<>(p.getTotal(), p.getResult());
    }
}
