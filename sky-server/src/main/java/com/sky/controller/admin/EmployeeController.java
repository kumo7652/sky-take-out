package com.sky.controller.admin;

import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.vo.EmployeeLoginVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
     * 新增员工
     * @param employeeDTO 新增员工对象
     * @return Result
     */
    @PostMapping
    public Result save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工：{}", employeeDTO);
        employeeService.save(employeeDTO);

        return Result.success();
    }

    /**
     * 分页查询员工
     * @param employeePageQueryDTO 分页查询参数对象
     * @return Result
     */
    @GetMapping("/page")
    public Result page(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("员工分页查询参数：{}", employeePageQueryDTO);
        PageResult<Employee> pageResult = employeeService.page(employeePageQueryDTO);

        return Result.success(pageResult);
    }

    /**
     * 启用、禁用员工
     * @param status 员工状态
     * @param id 员工id
     * @return Result
     */
    @PostMapping("/status/{status}")
    public Result switchStatus(@PathVariable Integer status, @RequestParam Long id) {
        log.info("启用禁用员工账号[id：{}, status：{}]", id, status);
        employeeService.switchStatus(status, id);
        return Result.success();
    }

    /**
     * 根据id查询员工
     * @param id 员工id
     * @return Result
     */
    @GetMapping("/{id}")
    public Result getEmpById(@PathVariable Long id) {
        log.info("查询员工{}", id);
        Employee employee = employeeService.getEmpById(id);

        return Result.success(employee);
    }

    /**
     * 更新员工数据
     * @param employeeDTO 更新员工对象
     * @return Result
     */
    @PutMapping
    public Result update(@RequestBody EmployeeDTO employeeDTO) {
        log.info("更新员工：{}", employeeDTO);
        employeeService.update(employeeDTO);

        return Result.success();
    }

    /**
     * 更改用户密码
     * @param passwordDTO 更新密码对象
     * @return Result
     */
    @PutMapping("/editPassword")
    public Result editPassword(@RequestBody PasswordDTO passwordDTO) {
        log.info("员工{}更改密码", BaseContext.getCurrentId());
        employeeService.changePassword(passwordDTO);
        return Result.success();
    }
}
