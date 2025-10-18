package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {
    private final DishMapper dishMapper;
    private final DishFlavorMapper dishFlavorMapper;

    /**
     * 新增菜品
     * @param dishDTO 新增菜品对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDishAndFlavors(DishDTO dishDTO) {
        // 解析数据
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        List<DishFlavor> dishFlavors = dishDTO.getFlavors();

        // 插入菜品表
        dishMapper.insert(dish);

        // 处理菜品口味表
        if  (dishFlavors != null && !dishFlavors.isEmpty()) {
            log.info("尝试插入风味数：{}", dishFlavors.size());

            // 过滤并设置菜品ID
            List<DishFlavor> flavors = dishFlavors.stream()
                    .filter(flavor -> !"".equals(flavor.getName()) && !flavor.getValue().isEmpty())
                    .peek(flavor -> flavor.setDishId(dishDTO.getId()))
                    .collect(Collectors.toList());

            // 有效风味数不为0时，插入风味表
            log.info("有效风味数：{}", flavors.size());
            if(!flavors.isEmpty()){
                dishFlavorMapper.insert(flavors);
            }
        }
    }

    /**
     * 分页查询菜品
     * @param dishPageQueryDTO 分页查询菜品对象
     */
    @Override
    public PageResult<DishVO> page(DishPageQueryDTO dishPageQueryDTO) {
        Page<DishVO> p = PageHelper
                .startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize())
                .doSelectPage(() -> dishMapper.pageQuery(dishPageQueryDTO));

        return new PageResult<>(p.getTotal(), p.getResult());
    }
}
