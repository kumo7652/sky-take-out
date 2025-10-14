package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理
 */
@Slf4j
@RestController
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * 新增分类
     * @param categoryDTO 分类对象
     * @return Result
     */
    @PostMapping
    public Result save(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类：{}", categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }

    /**
     * 分页查询分类
     * @param categoryPageQueryDTO 查询条件对象
     * @return Result
     */
    @GetMapping("/page")
    public Result page(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分页查询分类：{}", categoryPageQueryDTO);
        PageResult<Category> result = categoryService.page(categoryPageQueryDTO);

        return Result.success(result);
    }

    /**
     * 根据类型查询分类
     * @param type 类型
     * @return Result
     */
    @GetMapping("/list")
    public Result list(@RequestParam(required = false) Integer type) {
        log.info("查询类型为{}的分类", type);
        List<Category> list = categoryService.list(type);

        return Result.success(list);
    }

    /**
     * 启用或禁用分类
     * @param status 状态
     * @param id 分类id
     * @return Result
     */
    @PostMapping("/status/{status}")
    public Result switchStatus(@PathVariable Integer status,@RequestParam Long id) {
        log.info("启用禁用分类[id：{}, status：{}]", id, status);
        categoryService.switchStatus(status, id);

        return Result.success();
    }

    /**
     * 修改分类
     * @param categoryDTO 更新分类对象
     * @return Result
     */
    @PutMapping
    public Result update(@RequestBody CategoryDTO categoryDTO) {
        log.info("修改分类：{}", categoryDTO);
        categoryService.update(categoryDTO);

        return Result.success();
    }
}
