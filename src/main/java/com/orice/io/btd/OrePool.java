package com.orice.io.btd;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

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
    private Date start;
    /** 终止日期 **/
    private Date end;
    /** 已运行天数 **/
    private int runningDays;
    /** 已产出 **/
    private double realOutput;
    /** 一天的总毫米数 **/
    private final static long dayMillis = 1000L*3600L*24L;
    /** 一天预计产出 **/
    private final double perOut;

    public OrePool(double cost, double expectOutput, Date start, int period){
        this.cost = cost;
        this.expectOutput = expectOutput;
        this.period = period;
        this.perOut = expectOutput / period;
        setStart(start);
    }

    public OrePool(PoolType type, Date date){
        this(type.cost, type.output, date, type.period);
    }

    public void setStart(Date start){
        if (start != null){
            this.start = start;
            this.end = plusDays(start, period);
        }
    }

    /**
     * 计算指定天数后的日期
     * @param date
     * @param days
     * @return
     */
    private Date plusDays(Date date, int days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 向前天数 days
        calendar.add(Calendar.DATE, days);
        // 向后一秒
        calendar.add(Calendar.SECOND, -1);
        return calendar.getTime();
    }

    /**
     * 获取运行天数
     * @return
     */
    public int getRunningDays(){
        Date current = new Date();
        if (current.before(start)){
            return 0;
        }
        if (current.after(end)){
            return 0;
        }
        long betweenDays = (System.currentTimeMillis() - start.getTime()) / dayMillis;
        // 运行当天已可获取收益，天数+1
        return (int) betweenDays + 1;
    }

    /**
     * 矿池是否在有效期
     * @return
     */
    public boolean isAvailable(Date date){
        // [start, end]
        return !date.before(start) && !date.after(end);
    }

    /**
     * 核算当天收益
     * @return
     */
    public double profit(Date date){
        if (isAvailable(date)){
            return perOut;
        }
        return 0;
    }

    /**
     * 获取当前产出
     * @return
     */
    public double getRealOutput(){
        return getRunningDays() * 1.0 * perOut;
    }
}