package com.sky.controller.admin;

import com.sky.dto.SetMealDTO;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
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
    public Result save(@RequestBody SetMealDTO setMealDTO) {
        log.info("保存套餐信息：{}", setMealDTO);
        setMealService.save(setMealDTO);

        return Result.success();
    }
}
