package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {
    /**
     * 插入订单数据
     * @param orders 订单数据
     */
    void insert(Orders orders);


    /**
     * 根据订单号查询订单
     * @param orderNumber 订单号
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     * @param orders 订单信息
     */
    void update(Orders orders);

    /**
     * 根据id查询订单
     * @param id 订单id
     * @return 订单信息
     */
    Orders getById(Long id);

    /**
     * 条件分页查询
     * @param ordersPageQueryDTO 查询条件
     * @return 查询结果
     */
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 查看各种状态的订单
     * @param status 订单状态
     * @return 订单数
     */
    Integer countByStatus(Integer status);

    /**
     * 查询超时订单
     * @param status 订单状态
     * @param orderTime 下单时间
     * @return 超时订单
     */
    List<Orders> getByStatusAndOrderTimeLT(Integer status, LocalDateTime orderTime);
}
