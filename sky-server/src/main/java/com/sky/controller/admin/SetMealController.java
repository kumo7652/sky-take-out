package com.sky.controller.admin;

import com.sky.dto.SetMealDTO;
import com.sky.dto.SetMealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.SetMealVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController("adminSetMealController")
@RequiredArgsConstructor
@RequestMapping("/admin/setmeal")
public class SetMealController {
    private final SetMealService setMealService;

    /**
     * 新增菜品信息
     * @param setMealDTO 新增菜品信息对象
     * @return Result
     */
    @PostMapping
    @CacheEvict(cacheNames = "set_meal_cache", key = "#setMealDTO.categoryId")
    public Result save(@RequestBody SetMealDTO setMealDTO) {
        log.info("保存套餐信息：{}", setMealDTO);
        setMealService.save(setMealDTO);

        return Result.success();
    }

    /**
     * 分页查询套餐信息
     * @param setMealPageQueryDTO 分页查询参数
     * @return Result
     */
    @GetMapping("/page")
    public Result page(SetMealPageQueryDTO setMealPageQueryDTO) {
        log.info("分页查询套餐：{}", setMealPageQueryDTO);
        PageResult<SetMealVO> pageResult = setMealService.page(setMealPageQueryDTO);

        return Result.success(pageResult);
    }

    /**
     * 通过id查询套餐信息
     * @param id 套餐id
     * @return Result
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        log.info("查询id为{}的套餐", id);
        SetMealVO setMealVO = setMealService.getSetMealVOById(id);

        return Result.success(setMealVO);
    }

    /**
     * 启用停用套餐
     * @param status 套餐状态
     * @param id 套餐id
     * @return Result
     */
    @PostMapping("/status/{status}")
    @CacheEvict(cacheNames = "set_meal_cache",allEntries = true)
    public Result switchStatus(@PathVariable Integer status, @RequestParam Long id) {
        log.info("启用停用套餐：[{}, {}]", id, status);
        setMealService.switchStatus(status, id);

        return Result.success();
    }

    /**
     * 修改套餐
     * @param setMealDTO 修改套餐对象
     * @return Result
     */
    @PutMapping
    @CacheEvict(cacheNames = "set_meal_cache",allEntries = true) // 可能出现修改分类情况
    public Result update(@RequestBody SetMealDTO setMealDTO) {
        log.info("修改套餐：{}", setMealDTO);
        setMealService.update(setMealDTO);

        return Result.success();
    }

    /**
     * 删除套餐
     * @param ids 套餐id
     * @return Result
     */
    @DeleteMapping
    @CacheEvict(cacheNames = "set_meal_cache",allEntries = true)
    public Result delete(@RequestParam List<Long> ids) {
        log.info("删除套餐：{}", ids.toString());
        setMealService.delete(ids);

        return Result.success();
    }
}
