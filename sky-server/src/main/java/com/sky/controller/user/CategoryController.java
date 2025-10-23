package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController("userCategoryController")
@RequestMapping("/user/category")
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * 查询分类
     * @param type 分类类型
     */
    @GetMapping("/list")
    public Result list(Integer type) {
        log.info("客户端查询分类");
        List<Category> list = categoryService.list(type);

        return Result.success(list);
    }

}
