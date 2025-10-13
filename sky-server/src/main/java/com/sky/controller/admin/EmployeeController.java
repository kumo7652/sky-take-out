package com.sky.controller.admin;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.vo.EmployeeLoginVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 员工管理
 */
@Slf4j
@RestController
@RequestMapping("/admin/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    /**
     * 登录
     * @param employeeLoginDTO 用于传输员工登录的对象
     * @return Result
     */
    @PostMapping("/login")
    public Result login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        EmployeeLoginVO employeeLoginVO = employeeService.login(employeeLoginDTO);

        // 登录成功返回视图对象
        return Result.success(employeeLoginVO);
    }

    /**
     * 退出登录
     * @return Result
     */
    @PostMapping("/logout")
    public Result logout() {
        return Result.success();
    }

    /**
     * @param employeeDTO 新增员工对象
     * @return Result
     */
    @PostMapping
    public Result save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工：{}", employeeDTO);
        employeeService.save(employeeDTO);

        return Result.success();
    }
}
