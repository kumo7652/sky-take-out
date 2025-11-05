package com.sky.controller.user;

import com.sky.context.BaseContext;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user/order")
@RestController("userOrderController")
public class OrderController {
    private final OrderService orderService;

    /**
     * 用户下单
     * @param ordersSubmitDTO 用户下单信息
     */
    @PostMapping("/submit")
    public Result submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
        log.info("用户：{}下单：{}", BaseContext.getCurrentId(), ordersSubmitDTO);
        OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);

        return Result.success(orderSubmitVO);
    }

    /**
     * 订单支付
     * @param ordersPaymentDTO 订单支付对象
     * @return 预支付交易单
     */
    @PutMapping("/payment")
    public Result payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);

        log.info("生成预支付交易单：{}", orderPaymentVO);
        return Result.success(orderPaymentVO);
    }

    /**
     * 历史订单查询
     */
    @GetMapping("/historyOrders")
    public Result historyOrders(OrdersPageQueryDTO  ordersPageQueryDTO) {
        log.info("用户查询历史订单：[{}, {}]", BaseContext.getCurrentId(), ordersPageQueryDTO);
        PageResult<OrderVO> pageResult = orderService.page4User(ordersPageQueryDTO);

        return Result.success(pageResult);
    }

    /**
     * 查看订单详情
     * @param id 订单id
     */
    @GetMapping("/orderDetail/{id}")
    public Result getById(@PathVariable Long id) {
        log.info("查看订单详情：{}", id);
        OrderVO orderVO = orderService.getById(id);

        return Result.success(orderVO);
    }

    /**
     * 取消订单
     */
    @PutMapping("/cancel/{id}")
    public Result cancel(@PathVariable Long id) {
        log.info("用户端取消订单：{}", id);
        orderService.cancel4User(id);

        return Result.success();
    }

    /**
     * 再来一单
     */
    @PostMapping("/repetition/{id}")
    public Result repetition(@PathVariable Long id) {
        log.info("用户再来一单：{}", id);
        orderService.repetition(id);
        return Result.success();
    }

    /**
     * 催单
     */
    @GetMapping("/reminder/{id}")
    public Result reminder(@PathVariable Long id) {
        log.info("客户催单");
        orderService.reminder(id);

        return Result.success();
    }
}
