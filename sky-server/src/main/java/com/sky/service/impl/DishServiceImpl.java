package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
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
    private final SetMealDishMapper setMealDishMapper;

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

    /**
     * 删除菜品
     * @param ids 菜品id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> ids) {
        ids.forEach(id -> {
           Dish dish = dishMapper.getById(id);

           // 检查菜品状态（是否起售，有起售菜品所有菜品都无法删除）
           if(dish.getStatus().equals(StatusConstant.ENABLE)) {
               throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
           }

           // 检查菜品套餐是否关联（如果有关联，所有菜品无法删除）
           List<Long> setMealIds = setMealDishMapper.getSetMealByDishId(id);
           if(setMealIds != null && !setMealIds.isEmpty()) {
               throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
           }
        });

        // 删除菜品
        dishMapper.deleteBatch(ids);
        // 删除菜品对应风味
        dishFlavorMapper.deleteBatch(ids);
    }

    /**
     * 启售禁售菜品
     * @param status 菜品状态
     * @param id     菜品id
     */
    @Override
    public void switchStatus(Integer status, Long id) {
        // 构建菜品对象
        Dish dish = Dish.builder()
                .id(id)
                .status(status)
                .build();

        dishMapper.update(dish);
    }

    /**
     * 根据id查询菜品
     * @param id 菜品id
     * @return DishVO
     */
    @Override
    public DishVO getDishVOById(Long id) {
        // 查询菜品信息
        Dish dish = dishMapper.getById(id);

        // 查询菜品风味信息
        List<DishFlavor> flavors = dishFlavorMapper.getFlavorByDishId(id);

        // 复制信息
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(flavors);

        return dishVO;
    }

    /**
     * 更新菜品信息
     * @param dishDTO 更新菜品信息对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DishDTO dishDTO) {
        // 解析数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        // 更新菜品数据
        dishMapper.update(dish);

        // 更新菜品风味数据
        if(flavors != null && !flavors.isEmpty()) {
            // 使用先删除再插入的方式更新
            dishFlavorMapper.deleteBatch(List.of(dish.getId()));

            log.info("尝试更新插入风味数：{}", flavors.size());

            // 过滤并设置菜品ID
            flavors = flavors.stream()
                    .filter(flavor -> !"".equals(flavor.getName()) && !flavor.getValue().isEmpty())
                    .peek(flavor -> flavor.setDishId(dishDTO.getId()))
                    .collect(Collectors.toList());

            // 有效风味数不为0时，插入风味表
            log.info("更新插入有效风味数：{}", flavors.size());
            if(!flavors.isEmpty()){
                dishFlavorMapper.insert(flavors);
            }
        }
    }

    /**
     * 根据分类id查询菜品列表
     * @param categoryId 分类id
     * @return 菜品列表
     */
    @Override
    public List<Dish> listByCategoryId(Long categoryId) {
        return dishMapper.listByCategoryId(categoryId);
    }
}
