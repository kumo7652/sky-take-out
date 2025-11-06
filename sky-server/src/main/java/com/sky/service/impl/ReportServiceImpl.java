package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
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
        List<LocalDate> dateList = getDateList(begin, end);

        // 统计营业额
        List<BigDecimal> turnoverList = new ArrayList<>();
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = date.atStartOfDay();
            LocalDateTime endTime = date.plusDays(1).atStartOfDay();

            BigDecimal turnover = orderMapper.getTurnOver(beginTime, endTime);
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
        List<LocalDate> dateList = getDateList(begin, end);

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

    /**
     * 统计指定时间区间内订单数、有效订单数
     * @param begin 时间开始
     * @param end   时间结束
     * @return 订单数、有效订单数
     */
    @Override
    public OrderReportVO getOrdersStatistics(LocalDate begin, LocalDate end) {
        // 计算日期列表
        List<LocalDate> dateList = getDateList(begin, end);

        // 统计每日订单数、有效订单数
        List<Integer> orderCountList = new ArrayList<>();
        List<Integer> validOrderCountList = new ArrayList<>();

        Integer totalOrderCount = 0;
        Integer totalValidOrderCount = 0;

        Map<String, Object> con = new HashMap<>();

        for (LocalDate date : dateList) {
            LocalDateTime beginTime = date.atStartOfDay();
            LocalDateTime endTime = date.plusDays(1).atStartOfDay();

            con.put("beginTime", beginTime);
            con.put("endTime", endTime);

            Integer orderCount = orderMapper.getOrderCount(con);
            orderCountList.add(orderCount);
            totalOrderCount += orderCount;

            con.put("status", Orders.COMPLETED);

            Integer validOrderCount = orderMapper.getOrderCount(con);
            validOrderCountList.add(validOrderCount);
            totalValidOrderCount += validOrderCount;

            con.remove("status");
        }

        return OrderReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .orderCountList(StringUtils.join(orderCountList, ","))
                .validOrderCountList(StringUtils.join(validOrderCountList, ","))
                .totalOrderCount(totalOrderCount)
                .validOrderCount(totalValidOrderCount)
                .orderCompletionRate(totalValidOrderCount.doubleValue() / totalOrderCount.doubleValue())
                .build();
    }

    /**
     * 统计制定时间内销量前十商品
     * @param begin 时间开始
     * @param end   时间结束
     * @return 销量前十商品
     */
    @Override
    public SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end) {
        LocalDateTime beginTime = begin.atStartOfDay();
        LocalDateTime endTime = end.plusDays(1).atStartOfDay();

        List<GoodsSalesDTO> goodsSalesDTOList = orderMapper.getSalesTop10(beginTime, endTime);

        List<String> nameList = goodsSalesDTOList.stream().map(GoodsSalesDTO::getName).toList();
        List<Integer> numberList = goodsSalesDTOList.stream().map(GoodsSalesDTO::getNumber).toList();

        return SalesTop10ReportVO.builder()
                .nameList(StringUtils.join(nameList, ","))
                .numberList(StringUtils.join(numberList, ","))
                .build();
    }

    private List<LocalDate> getDateList(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();

        while (!begin.isAfter(end)) {
            dateList.add(begin);
            begin = begin.plusDays(1);
        }
        return dateList;
    }
}
