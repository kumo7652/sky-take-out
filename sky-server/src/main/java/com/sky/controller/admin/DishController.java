package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 分页查询菜品
     * @param dishPageQueryDTO 分页查询菜品对象
     * @return Result
     */
    @GetMapping("/page")
    public Result page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("分页查询菜品：{}", dishPageQueryDTO);
        PageResult<DishVO> pageResult = dishService.page(dishPageQueryDTO);

        return Result.success(pageResult);
    }

    /**
     * 删除菜品
     * @param ids 菜品id
     * @return Result
     */
    @DeleteMapping
    public Result delete(@RequestParam List<Long> ids) {
        log.info("删除菜品：{}", ids);
        dishService.delete(ids);

        return Result.success();
    }

    /**
     * 启售禁售菜品
     * @param status 菜品状态
     * @param id 菜品id
     * @return Result
     */
    @PostMapping("/status/{status}")
    public Result switchStatus(@PathVariable Integer status, @RequestParam Long id) {
        log.info("启售禁售菜品：{}", id);
        dishService.switchStatus(status, id);

        return Result.success();
    }
}
