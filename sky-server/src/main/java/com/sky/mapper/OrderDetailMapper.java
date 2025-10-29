package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    /**
     * 批量插入订单明细
     * @param orderDetailList 订单明细列表
     */
    void insertBatch(List<OrderDetail> orderDetailList);

    /**
     * 通过订单id查询订单明细表
     * @param orderId 订单id
     * @return 对应明细
     */
    List<OrderDetail> getByOrderId(Long orderId);
}
