package com.sky.controller.admin;


import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/report")
@RestController
public class ReportController {
    private final ReportService reportService;

    /**
     * 营业额统计
     */
    @GetMapping("/turnoverStatistics")
    public Result turnoverStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate begin,
                                     @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate end) {
        log.info("统计从{}到{}的营业额", begin, end);
        TurnoverReportVO turnoverReportVO = reportService.getTurnOverStatistics(begin, end);

        return Result.success(turnoverReportVO);
    }

    /**
     * 用户统计
     */
    @GetMapping("/userStatistics")
    public Result userStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate begin,
                                 @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate end) {
        log.info("统计从{}到{}用户数", begin, end);
        UserReportVO userReportVO = reportService.getUserStatistics(begin, end);

        return Result.success(userReportVO);
    }

    /**
     * 订单统计
     */
    @GetMapping("/ordersStatistics")
    public Result ordersStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate begin,
                                   @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate end) {
        log.info("统计从{}到{}订单数、有效订单数", begin, end);
        OrderReportVO orderReportVO = reportService.getOrdersStatistics(begin, end);

        return Result.success(orderReportVO);
    }

    /**
     * 销量前十统计
     */
    @GetMapping("/top10")
    public Result top10(@DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate begin,
                        @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate end) {
        log.info("统计从{}到{}销量前十商品", begin, end);
        SalesTop10ReportVO salesTop10ReportVO = reportService.getSalesTop10(begin, end);

        return Result.success(salesTop10ReportVO);
    }
}
