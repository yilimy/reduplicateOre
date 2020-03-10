package com.orice.io.btd;

import com.orice.io.btd.bean.PoolType;
import com.orice.io.btd.bean.RunningPool;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 复投项目
 *
 * @author caimeng
 * @date 2020/2/23 15:10
 */
@Data
@Slf4j
public class Business {
    /** 持有的btd数量 **/
    private static double btd;
    /** 当天收益 **/
    private static double p;
    /** 已运行的btd数量 **/
    private List<RunningPool> pools = new ArrayList<>();

    public Business(double b){
        btd = b;
    }

    private void deal(){
        // 1. 结算昨天收益
    }

    /**
     * 取现
     */
    public void enCash(double d){
        if (d > btd){
            log.error("{} > {}", d, btd);
            throw new RuntimeException("enCash failed .");
        }
        btd = btd -d;
    }

    /**
     * 添加一个矿池
     * @param type
     * @param date
     */
    public void addPool(PoolType type, Date date){
        RunningPool runningPool = new RunningPool();
        btd = runningPool.buyPool(type, date, btd);
        log.info("add {}", runningPool);
        log.info("btd left {}", btd);
        pools.add(runningPool);
    }

    /**
     * 结算累计收益
     * @param date
     * @return
     */
    public void profit(Date date){
        p = 0;
        pools.stream().forEach(item -> p += item.profit(date));
        log.info("profit : {}", p);
        btd += p;
    }

    /**
     * 持有的BTD数量
     * @return
     */
    public double holds(){
        return btd;
    }
}
