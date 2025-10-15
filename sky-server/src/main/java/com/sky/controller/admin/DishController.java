package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.result.Result;
import com.sky.service.DishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜品管理
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/dish")
public class DishController {
    private final DishService dishService;

    /**
     * 新增菜品
     * @param dishDTO 新增菜品对象
     * @return Result
     */
    @PostMapping
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品：{}", dishDTO);
        dishService.saveDishAndFlavors(dishDTO);
        return Result.success();
    }
}
