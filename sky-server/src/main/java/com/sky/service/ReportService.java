package com.sky.service;

import com.sky.vo.TurnoverReportVO;

import java.time.LocalDate;

public interface ReportService {
    /**
     * 统计制定时间区间内营业额
     * @param begin 时间开始
     * @param end 时间结束
     * @return 营业额数据
     */
    TurnoverReportVO getTurnOverStatistics(LocalDate begin, LocalDate end);
}
