package com.sky.controller.admin;

import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/order")
@RestController("adminOrderController")
public class OrderController {
    private final OrderService orderService;

    /**
     * 接单
     */
    @PutMapping("/confirm")
    public Result confirm(@RequestBody OrdersConfirmDTO ordersConfirmDTO) {
        log.info("接单：{}", ordersConfirmDTO);
        orderService.confirm(ordersConfirmDTO);

        return Result.success();
    }

    /**
     * 条件分页查询订单
     * @param ordersPageQueryDTO 查询条件
     * @return 查询结果
     */
    @GetMapping("/conditionSearch")
    public Result page(OrdersPageQueryDTO ordersPageQueryDTO) {
        log.info("条件分页查询订单：{}", ordersPageQueryDTO);
        PageResult<OrderVO> pageResult = orderService.page(ordersPageQueryDTO);

        return Result.success(pageResult);
    }

    /**
     * 统计各个状态订单数量
     */
    @GetMapping("/statistics")
    public Result statistics() {
        OrderStatisticsVO orderStatisticsVO = orderService.statistics();
        return Result.success(orderStatisticsVO);
    }

    /**
     * 查询订单详情
     */
    @GetMapping("/details/{id}")
    public Result details(@PathVariable Long id) {
        log.info("查询订单，id：{}", id);
        OrderVO orderVO = orderService.getById(id);

        return Result.success(orderVO);
    }
}
