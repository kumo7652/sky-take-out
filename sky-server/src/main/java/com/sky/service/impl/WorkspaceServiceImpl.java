package com.sky.service.impl;

import com.sky.constant.StatusConstant;
import com.sky.entity.Orders;
import com.sky.mapper.DishMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetMealOverViewVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
/*
 * 营业额：当日已完成订单的总金额
 * 有效订单：当日已完成订单的数量
 * 订单完成率：有效订单数 / 总订单数
 * 平均客单价：营业额 / 有效订单数
 * 新增用户：当日新增用户的数量
 */
public class WorkspaceServiceImpl implements WorkspaceService {
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;
    private final DishMapper dishMapper;
    private final SetMealMapper setmealMapper;

    /**
     * 根据时间段统计营业数据
     * @param begin 时间开始
     * @param end 时间结束
     * @return 营业数据
     */
    public BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end) {
        Map<String, Object> map = new HashMap<>();
        map.put("begin",begin);
        map.put("end",end);

        //查询总订单数
        Integer totalOrderCount = orderMapper.getOrderCount(map);

        map.put("status", Orders.COMPLETED);

        //营业额
        BigDecimal turnover = orderMapper.getTurnOver(begin, end);
        turnover = turnover == null? BigDecimal.ZERO : turnover;

        //有效订单数
        Integer validOrderCount = orderMapper.getOrderCount(map);

        BigDecimal unitPrice = BigDecimal.ZERO;

        double orderCompletionRate = 0.0;
        if(totalOrderCount != 0 && validOrderCount != 0){
            //订单完成率
            orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
            //平均客单价
            unitPrice = turnover.divide(
                    new BigDecimal(validOrderCount),
                    2,
                    RoundingMode.HALF_UP
            );
        }

        //新增用户数
        Integer newUsers = userMapper.getTodayNewUserAmount(begin, end);

        return BusinessDataVO.builder()
                .turnover(turnover)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .unitPrice(unitPrice)
                .newUsers(newUsers)
                .build();
    }


    /**
     * 查询订单管理数据
     */
    public OrderOverViewVO getOrderOverView() {
        Map<String, Object> map = new HashMap<>();
        map.put("begin", LocalDateTime.now().with(LocalTime.MIN));
        map.put("status", Orders.TO_BE_CONFIRMED);

        //待接单
        Integer waitingOrders = orderMapper.getOrderCount(map);

        //待派送
        map.put("status", Orders.CONFIRMED);
        Integer deliveredOrders = orderMapper.getOrderCount(map);

        //已完成
        map.put("status", Orders.COMPLETED);
        Integer completedOrders = orderMapper.getOrderCount(map);

        //已取消
        map.put("status", Orders.CANCELLED);
        Integer cancelledOrders = orderMapper.getOrderCount(map);

        //全部订单
        map.put("status", null);
        Integer allOrders = orderMapper.getOrderCount(map);

        return OrderOverViewVO.builder()
                .waitingOrders(waitingOrders)
                .deliveredOrders(deliveredOrders)
                .completedOrders(completedOrders)
                .cancelledOrders(cancelledOrders)
                .allOrders(allOrders)
                .build();
    }

    /**
     * 查询菜品总览
     */
    public DishOverViewVO getDishOverView() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", StatusConstant.ENABLE);
        Integer sold = dishMapper.countByMap(map);

        map.put("status", StatusConstant.DISABLE);
        Integer discontinued = dishMapper.countByMap(map);

        return DishOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }

    /**
     * 查询套餐总览
     */
    public SetMealOverViewVO getSetmealOverView() {
        Map<String, Object> map = new HashMap<>();

        map.put("status", StatusConstant.DISABLE);
        Integer discontinued = setmealMapper.countByMap(map);

        map.put("status", StatusConstant.ENABLE);
        Integer sold = setmealMapper.countByMap(map);

        return SetMealOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }
}
