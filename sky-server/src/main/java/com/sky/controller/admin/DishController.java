package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜品管理
 */
@Slf4j
@RestController("adminDishController")
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
    @CacheEvict(cacheNames = "dishCache", key = "#dishDTO.categoryId")
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
    @CacheEvict(cacheNames = "dishCache", allEntries = true)
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
    @CacheEvict(cacheNames = "dishCache", allEntries = true)
    public Result switchStatus(@PathVariable Integer status, @RequestParam Long id) {
        log.info("启售禁售菜品：{}", id);
        dishService.switchStatus(status, id);

        return Result.success();
    }

    /**
     * 根据id查询菜品
     * @param id 菜品id
     * @return Result
     */
    @GetMapping("/{id}")
    public Result getDishById(@PathVariable Long id) {
        log.info("查询菜品{}信息", id);
        DishVO dishVO = dishService.getDishVOById(id);

        return Result.success(dishVO);
    }

    /**
     * 更新菜品信息
     * @param dishDTO 更新菜品信息对象
     * @return Result
     */
    @PutMapping
    @CacheEvict(cacheNames = "dishCache", allEntries = true)
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("更新菜品信息：{}",  dishDTO);
        dishService.update(dishDTO);

        return Result.success();
    }

    /**
     * 根据分类查询菜品
     * @param categoryId 分类id
     * @return Result
     */
    @GetMapping("/list")
    public Result list(@RequestParam Long categoryId) {
        log.info("查找{}分类下菜品", categoryId);
        List<Dish> list = dishService.listByCategoryId(categoryId);

        return Result.success(list);
    }
}
