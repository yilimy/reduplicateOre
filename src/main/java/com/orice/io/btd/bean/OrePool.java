package com.orice.io.btd.bean;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/**
 * 标准矿池模型
 * @author caimeng
 * @date 2020/2/23 13:35
 */
@ToString(exclude = {"perOut"})
@Getter
@Slf4j
public class OrePool {
    /** 投入、成本、消耗的BTD **/
    private double cost;
    /** 预计产出产出 **/
    private double expectOutput;
    /** 周期 **/
    private int period;
    /** 起投日期 **/
    private LocalDate start;
    /** 终止日期 **/
    private LocalDate end;
    /** 已运行天数 **/
    private int runningDays;
    /** 已产出 **/
    private double realOutput;
    /** 一天的总毫米数 **/
    private final static long dayMillis = 1000L*3600L*24L;
    /** 一天预计产出 **/
    private final double perOut;
    /** 矿池类型 **/
    private PoolType type;

    public OrePool(double cost, double expectOutput, LocalDate start, int period){
        this.cost = cost;
        this.expectOutput = expectOutput;
        this.period = period;
        this.perOut = expectOutput / period;
        setStart(start);
    }

    public OrePool(PoolType type, LocalDate date){
        this(type.getCost(), type.getOutput(), date, type.getPeriod());
        this.type = type;
    }

    public void setStart(LocalDate start){
        if (start != null){
            this.start = start;
            // 终止日期的23:59:59秒，以前一天算
            this.end = start.plusDays(period - 1);
        }
    }

    /**
     * 是否可以续期
     * @param date
     * @return
     */
    public boolean canRenewal(LocalDate date){
        // 最后一天，及其之后
        return !end.isAfter(date);
    }

    /**
     * 矿池是否在有效期  [start, end]
     * @return
     */
    public boolean isAvailable(LocalDate date){
        boolean flag = !date.isBefore(start) && !date.isAfter(end);
        if (!flag){
            log.info("已过期 : {}", this);
        }
        return flag;
    }

    /**
     * 核算当天收益
     * @return
     */
    public double profit(LocalDate date){
        if (isAvailable(date)){
            return perOut;
        }
        return 0;
    }
}
