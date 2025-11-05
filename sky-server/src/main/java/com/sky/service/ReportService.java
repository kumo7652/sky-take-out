package com.sky.service;

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
     * 通知指定时间区间内用户数
     * @param begin 时间开始
     * @param end 时间结束
     * @return 用户数
     */
    UserReportVO getUserStatistics(LocalDate begin, LocalDate end);
}
