package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
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
}
