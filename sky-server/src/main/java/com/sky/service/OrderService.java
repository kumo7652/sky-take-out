package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {
    /**
     * 用户下单
     * @param ordersSubmitDTO 用户下单信息
     * @return 订单信息
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * 订单支付
     * @param ordersPaymentDTO 订单支付对象
     * @return 预支付信息
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo 交易序号
     */
    void paySuccess(String outTradeNo);

    /**
     * 接单
     * @param ordersConfirmDTO 订单id
     */
    void confirm(OrdersConfirmDTO ordersConfirmDTO);

    /**
     * 条件分页查询订单
     * @param ordersPageQueryDTO 查询条件
     * @return 查询结果
     */
    PageResult<OrderVO> page(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 各个状态的订单数量统计
     * @return 统计结果
     */
    OrderStatisticsVO statistics();

    /**
     * 根据id查询订单
     * @param id 订单id
     * @return 订单
     */
    OrderVO getById(Long id);

    /**
     * 拒单
     * @param ordersRejectionDTO id以及原因
     */
    void reject(OrdersRejectionDTO ordersRejectionDTO);

    /**
     * 派送
     * @param id 订单id
     */
    void delivery(Long id);

    /**
     * 完成订单
     * @param id 订单id
     */
    void complete(Long id);

    /**
     * 管理端取消订单
     * @param ordersCancelDTO 订单取消对象
     */
    void cancel(OrdersCancelDTO ordersCancelDTO);

    /**
     * 用户条件分页查询
     * @param ordersPageQueryDTO 查询条件
     * @return 查询结果
     */
    PageResult<OrderVO> page4User(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 用户端取消订单
     * @param id 订单id
     */
    void cancel4User(Long id);

    /**
     * 再来一单
     * @param id 订单id
     */
    void repetition(Long id);
}
