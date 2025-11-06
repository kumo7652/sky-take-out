package com.sky.service;

import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetMealOverViewVO;
import java.time.LocalDateTime;

public interface WorkspaceService {

    /**
     * 根据时间段统计营业数据
     * @param begin 时间开始
     * @param end 时间结束
     * @return 营业数据
     */
    BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end);

    /**
     * 查询订单管理数据
     * @return 订单数据
     */
    OrderOverViewVO getOrderOverView();

    /**
     * 查询菜品总览
     * @return 菜品
     */
    DishOverViewVO getDishOverView();

    /**
     * 查询套餐总览
     * @return 套餐
     */
    SetMealOverViewVO getSetmealOverView();

}
