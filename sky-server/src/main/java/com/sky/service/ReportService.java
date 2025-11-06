package com.sky.service;

import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import java.time.LocalDate;

public interface ReportService {
    /**
     * 统计指定时间区间内营业额
     * @param begin 时间开始
     * @param end 时间结束
     * @return 营业额数据
     */
    TurnoverReportVO getTurnOverStatistics(LocalDate begin, LocalDate end);

    /**
     * 统计指定时间区间内用户数
     * @param begin 时间开始
     * @param end 时间结束
     * @return 用户数
     */
    UserReportVO getUserStatistics(LocalDate begin, LocalDate end);

    /**
     * 统计指定时间区间内订单数、有效订单数
     * @param begin 时间开始
     * @param end 时间结束
     * @return 订单数、有效订单数
     */
    OrderReportVO getOrdersStatistics(LocalDate begin, LocalDate end);

    /**
     * 统计制定时间内销量前十商品
     * @param begin 时间开始
     * @param end 时间结束
     * @return 销量前十商品
     */
    SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end);
}
