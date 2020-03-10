package com.orice.io.btd;

import com.orice.io.btd.bean.PoolType;
import com.orice.io.btd.bean.RunningPool;
import com.orice.io.btd.strategy.InvestStrategy;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.*;

/**
 * 复投项目
 *
 * @author caimeng
 * @date 2020/2/23 15:10
 */
@Slf4j
public class Business {
    /** 持有的btd数量 **/
    private double btd;
    /** 当天收益 **/
    private static double p;
    /** 已运行的btd数量 **/
    private List<RunningPool> pools = new ArrayList<>();
    /** 策略详情 **/
    @Setter
    private List<InvestStrategy> strategies = new ArrayList<>();

    public Business(double b){
        this.btd = b;
    }

    /**
     * 开始复投 [start, end)
     * @param start
     * @param end
     */
    public void start(LocalDate start, LocalDate end){
        for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)){
            System.out.println("");
            log.info("date : {}", date);
            processDate(date);
        }
    }

    /**
     * 检查每天的复投
     * @param date
     */
    private void processDate(LocalDate date) {
        // 核算前一天收益
        profit(date);
        // 检查到指定日期有策略，执行策略
        strategies.stream().forEach(item -> {
            if (date.isEqual(item.getDate())){
                log.info("check strategy {}", item);
                item.deal(this);
            }
        });
        // 触发自动购买策略，10w, 4w
        pools.stream().forEach(item -> autoRenewal(item, date));
        // 当日结算持有
        System.out.printf("%s total btd %s\n", date, btd);
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
    public void addPool(PoolType type, LocalDate date){
        RunningPool runningPool = new RunningPool();
        btd = runningPool.buyPool(type, date, btd);
        log.info("add {}", runningPool);
        log.info("btd left {}", btd);
        pools.add(runningPool);
    }

    /**
     * 自动续期
     */
    private void autoRenewal(RunningPool pool, LocalDate date){
        if (!pool.isEndDay(date)){
            return;
        }
        if (!pool.isAuto() || pool.getNext() != null){
            return;
        }
        //自动换成最新版的矿池
        PoolType type = pool.getRunning().getType();
        if (type == null){
            throw new RuntimeException("pool no type .");
        }
        String typeStr = type.name();
        typeStr = typeStr.substring(0, typeStr.length()-1) + "2";
        PoolType typeNew = PoolType.matches(typeStr);
        btd = pool.buyPool(typeNew, date, btd);
        log.info("auto renewal pool .");
    }

    /**
     * 结算累计收益
     * @param date
     * @return
     */
    private void profit(LocalDate date){
        p = 0;
        pools.stream().forEach(item -> p += item.profit(date));
        log.info("profit : {}", p);
        btd += p;
    }

    /**
     * 持有的BTD数量
     * @return
     */
    private double holds(){
        return btd;
    }
}
