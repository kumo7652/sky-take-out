package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetMealDTO;
import com.sky.dto.SetMealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.SetMeal;
import com.sky.entity.SetMealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetMealEnableFailedException;
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

    /**
     * 根据id查询套餐信息
     * @param id 套餐id
     * @return 套餐信息
     */
    @Override
    public SetMealVO getSetMealVOById(Long id) {
        SetMealVO setMealVO = new SetMealVO();

        // 查询套餐信息
        SetMeal setMeal = setMealMapper.getById(id);

        // 查询关联菜品信息
        List<SetMealDish> setMealDishes = setMealDishMapper.getBySetMealId(id);

        BeanUtils.copyProperties(setMeal, setMealVO);
        setMealVO.setSetMealDishes(setMealDishes);
        return setMealVO;
    }

    /**
     * 启用停用套餐
     * @param status 套餐状态
     * @param id     套餐id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void switchStatus(Integer status, Long id) {
        // 获取套餐关联菜品
        List<Dish> dishes = setMealDishMapper.getDishBySetMealId(id);
        for (Dish dish : dishes) {
            // 菜品启售时，套餐可以启售禁售
            // 菜品禁售时，套餐无法启售
            if (dish.getStatus().equals(StatusConstant.DISABLE)) {
                throw new SetMealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
            }
        }

        // 构建套餐对象
        SetMeal setMeal = SetMeal.builder()
                .id(id)
                .status(status)
                .build();

        setMealMapper.update(setMeal);
    }

    /**
     * 修改套餐
     * @param setMealDTO 修改套餐对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SetMealDTO setMealDTO) {
        // 解析数据
        SetMeal setMeal = new SetMeal();
        BeanUtils.copyProperties(setMealDTO, setMeal);
        List<SetMealDish> setMealDishes = setMealDTO.getSetMealDishes();

        // 修改套餐信息
        setMealMapper.update(setMeal);

        // 修改套餐关联菜品信息
        if (setMealDishes != null && !setMealDishes.isEmpty()) {
            setMealDishMapper.deleteBatch(List.of(setMeal.getId()));

            setMealDishes.forEach(setMealDish -> setMealDish.setSetMealId(setMeal.getId()));
            setMealDishMapper.insert(setMealDishes);
        }
   }

    /**
     * 批量删除套餐
     * @param ids 套餐id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> ids) {
        // 获取套餐状态
        ids.forEach(id -> {
            SetMeal setMeal = setMealMapper.getById(id);
            if(setMeal.getStatus().equals(StatusConstant.ENABLE)) {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        });

        // 解除套餐菜品关联
        setMealDishMapper.deleteBatch(ids);

        // 删除套餐
        setMealMapper.deleteBatch(ids);
    }
}
