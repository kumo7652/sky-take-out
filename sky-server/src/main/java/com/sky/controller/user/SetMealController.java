package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.SetMeal;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.DishItemVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController("userSetMealController")
@RequestMapping("/user/setmeal")
public class SetMealController {
    private final SetMealService setMealService;

    /**
     * 条件查询
     * @param categoryId 分类id
     */
    @GetMapping("/list")
    @Cacheable(cacheNames = "set_meal_cache", key="#categoryId")
    public Result list(Long categoryId) {
        SetMeal setmeal = new SetMeal();
        setmeal.setCategoryId(categoryId);
        setmeal.setStatus(StatusConstant.ENABLE);

        List<SetMeal> list = setMealService.list(setmeal);
        return Result.success(list);
    }

    /**
     * 根据套餐id查询包含的菜品列表
     * @param id 套餐id
     */
    @GetMapping("/dish/{id}")
    public Result dishList(@PathVariable("id") Long id) {
        List<DishItemVO> list = setMealService.getDishItemById(id);
        return Result.success(list);
    }
}
