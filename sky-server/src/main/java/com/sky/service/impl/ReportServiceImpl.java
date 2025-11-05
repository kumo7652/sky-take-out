package com.sky.service.impl;

import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;

    /**
     * 统计制定时间区间内营业额
     * @param begin 时间开始
     * @param end   时间结束
     * @return 营业额数据
     */
    @Override
    public TurnoverReportVO getTurnOverStatistics(LocalDate begin, LocalDate end) {
        // 计算日期列表
        List<LocalDate> dateList = new ArrayList<>();

        while (!begin.isAfter(end)) {
            dateList.add(begin);
            begin = begin.plusDays(1);
        }

        // 统计营业额
        List<BigDecimal> turnoverList = new ArrayList<>();
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = date.atStartOfDay();
            LocalDateTime endTime = date.plusDays(1).atStartOfDay();

            BigDecimal turnover = orderMapper.getTodayTurnOver(beginTime, endTime);
            turnoverList.add(turnover == null? BigDecimal.ZERO: turnover);
        }

        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .turnoverList(StringUtils.join(turnoverList, ","))
                .build();
    }

    /**
     * 通知指定时间区间内用户数
     * @param begin 时间开始
     * @param end   时间结束
     * @return 用户数
     */
    @Override
    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
        // 计算日期列表
        List<LocalDate> dateList = new ArrayList<>();

        while (!begin.isAfter(end)) {
            dateList.add(begin);
            begin = begin.plusDays(1);
        }

        // 统计用户总量、新增用户数
        List<Integer> totalList = new ArrayList<>();
        List<Integer> newUserList = new ArrayList<>();

        for (LocalDate date : dateList) {
            LocalDateTime beginTime = date.atStartOfDay();
            LocalDateTime endTime = date.plusDays(1).atStartOfDay();

            Integer totalAmount = userMapper.getTotalUserAmountSoFar(endTime);
            totalList.add(totalAmount);

            Integer newUserAmount = userMapper.getTodayNewUserAmount(beginTime, endTime);
            newUserList.add(newUserAmount);
        }

        return UserReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .totalUserList(StringUtils.join(totalList, ","))
                .newUserList(StringUtils.join(newUserList, ","))
                .build();
    }
}
