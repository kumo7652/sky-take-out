package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.*;
import com.sky.exception.AddressBookBusinessException;
import com.sky.exception.ShoppingCartBusinessException;
import com.sky.mapper.*;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final OrderDetailMapper orderDetailMapper;
    private final AddressBookMapper addressBookMapper;
    private final ShoppingCartMapper shoppingCartMapper;

    /**
     * 用户下单
     * @param ordersSubmitDTO 用户下单信息
     * @return 订单信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO) {
        // 处理各种异常（地址簿为空，购物车为空）
        AddressBook addressBook = addressBookMapper.getById(ordersSubmitDTO.getAddressBookId());
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.getByUserId(BaseContext.getCurrentId());

        if (addressBook == null){
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }

        if (shoppingCartList == null ||  shoppingCartList.isEmpty()){
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }

        // 插入订单信息
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO, orders);
        orders.setUserId(BaseContext.getCurrentId()); // 当前用户

        orders.setOrderTime(LocalDateTime.now()); // 下单时间
        orders.setPayStatus(Orders.UN_PAID); // 支付状态（未付款）
        orders.setStatus(Orders.PENDING_PAYMENT); // 订单状态（待付款）
        orders.setNumber(String.valueOf(System.currentTimeMillis())); // 订单号

        orders.setPhone(addressBook.getPhone()); // 手机号
        orders.setConsignee(addressBook.getConsignee()); // 收货人
        orders.setAddressBookId(addressBook.getId()); // 收货地址

        orderMapper.insert(orders);

        // 插入订单明细信息
        List<OrderDetail> orderDetailList = shoppingCartList.stream()
                .map(shoppingCart -> {
                    OrderDetail orderDetail = new OrderDetail();
                    BeanUtils.copyProperties(shoppingCart, orderDetail);

                    orderDetail.setOrderId(orders.getId());
                    return orderDetail;
                })
                .toList();

        orderDetailMapper.insertBatch(orderDetailList);

        // 清空用户购物车
        shoppingCartMapper.deleteByUserId(BaseContext.getCurrentId());

        // 返回视图对象
        return OrderSubmitVO.builder()
                .id(orders.getId())
                .orderAmount(orders.getAmount())
                .orderNumber(orders.getNumber())
                .orderTime(orders.getOrderTime())
                .build();
    }

    /**
     * 订单支付
     * @param ordersPaymentDTO 订单支付对象
     * @return 预支付信息
     */
    @Override
    public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) {
/*
        // 当前登录用户id
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.getById(userId);

        // 调用微信支付接口，生成预支付交易单
        JSONObject jsonObject = weChatPayUtil.pay(
                ordersPaymentDTO.getOrderNumber(), // 商户订单号
                new BigDecimal("0.01"), // 支付金额，单位 元
                "苍穹外卖订单", // 商品描述
                user.getOpenid() // 微信用户的openid
        );

        if (jsonObject.getString("code") != null && jsonObject.getString("code").equals("ORDERPAID")) {
            throw new OrderBusinessException("该订单已支付");
        }

        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
        vo.setPackageStr(jsonObject.getString("package"));
*/
        paySuccess(ordersPaymentDTO.getOrderNumber());

        return new OrderPaymentVO();
    }

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo 交易序号
     */
    @Override
    public void paySuccess(String outTradeNo) {
        // 根据订单号查询订单
        Orders ordersDB = orderMapper.getByNumber(outTradeNo);

        // 根据订单id更新订单的状态、支付方式、支付状态、结账时间
        Orders orders = Orders.builder()
                .id(ordersDB.getId())
                .status(Orders.TO_BE_CONFIRMED)
                .payStatus(Orders.PAID)
                .checkoutTime(LocalDateTime.now())
                .build();

        orderMapper.update(orders);
    }
}
