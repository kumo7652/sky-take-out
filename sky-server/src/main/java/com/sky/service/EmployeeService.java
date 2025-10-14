package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;
import com.sky.vo.EmployeeLoginVO;

public interface EmployeeService {


    /**
     * 员工登录
     * @param employeeLoginDTO 用于传输员工登录的对象
     * @return EmployeeLoginVo
     */
    EmployeeLoginVO login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     * @param employeeDTO 新增员工对象
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * 分页查询员工
     * @param employeePageQueryDTO 分页查询参数对象
     * @return PageResult
     */
    PageResult<Employee> page(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 启用禁用员工
     * @param status 员工状态
     * @param id 员工id
     */
    void switchStatus(Integer status, Long id);

    /**
     * 根据id查询员工
     * @param id 员工id
     * @return Employee
     */
    Employee getEmpById(Long id);

    /**
     * 更新员工
     * @param employeeDTO 更新员工对象
     */
    void update(EmployeeDTO employeeDTO);

    /**
     * 用户更改密码
     * @param passwordDTO 更新密码对象
     */
    void changePassword(PasswordDTO passwordDTO);
}
