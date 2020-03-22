package com.orice.io.btd.bean;

import com.orice.io.btd.bean.OrePool;
import com.orice.io.btd.bean.PoolType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/**
 * 正在运行的矿池
 *
 * @author caimeng
 * @date 2020/2/23 14:05
 */
@Data
@Slf4j
public class RunningPool {

    /** 正在运行的矿池 **/
    private OrePool running;

    /** 下次投入的矿池 **/
    private OrePool next;

    /** 自动续期配置 **/
    private boolean auto = true;

    /**
     * 购买一个矿池
     * @param type 矿池类型
     * @param date 购买日期
     * @param btd 持有BTD数量
     * @return
     */
    public double buyPool(PoolType type, LocalDate date, double btd){
        OrePool pool = new OrePool(type, null);
        // 检查BTD是否够付款
        if (pool.getCost() > btd){
            log.error("buy pool failed : BTD {} not enough to pay pool {}", btd, pool.getCost());
            throw new RuntimeException("not enough to pay pool .");
        }
        // 购买一个矿池
        if (running == null){
            // 购买矿池，次日生效
            pool.setStart(date.plusDays(1));
            running = pool;
            log.info("buy pool success {}", pool);
            return btd - pool.getCost();
        }
        // 购买一个续期矿池
        if (next == null){
            pool.setStart(date.plusDays(1));
            next = pool;
            log.info("buy renewal pool success {}", pool);
            return btd - pool.getCost();
        }
        log.error("has renewal Ore, buy failed - {}", date);
        throw new RuntimeException("has renewal Ore pool .");
    }

    /**
     * 结算矿池收益，当天
     * @return
     */
    public double profit(LocalDate date){
        // 没有正在运行的矿池
        if (running == null){
            return 0;
        }
        // 矿池正在运行
        if (running.isAvailable(date)){
            return running.profit(date);
        }
        // 没有矿池正在运行，运行续期矿池
        if (next != null){
            next.setStart(date);
            running = next;
            next = null;
            return running.profit(date);
        }
        return 0;
    }

    /**
     * 是否可以续期
     * @return
     */
    public boolean canRenewal(LocalDate date){
        if (running == null){
            log.info("running is null, has not end day .", running.getType());
            return false;
        }
        return running.canRenewal(date);
    }

}
