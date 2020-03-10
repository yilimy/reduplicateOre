package com.orice.io.btd.bean;

import lombok.Getter;

/**
 * @author caimeng
 * @date 2020/2/23 14:43
 */
@Getter
public enum PoolType {
    // 初级矿池
    JUNIOR_V1(100, 110, 30),
    // 中级矿池
    MIDDLE_V1(1000, 1200, 30),
    // 进阶矿池
    UPGRADE_V1(4000, 5000, 30),
    // 高级矿池
    HIGH_V1(10000, 12800, 30),
    // 精英矿池
    ELITE_V1(40000, 52000, 30),
    // 超级矿池
    SUPER_V1(100000, 135000, 30),

    // 初级矿池
    JUNIOR_V2(100, 110, 45),
    // 中级矿池
    MIDDLE_V2(1000, 1200, 45),
    // 进阶矿池
    UPGRADE_V2(4000, 5000, 45),
    // 高级矿池
    HIGH_V2(1000, 12800, 45),
    // 精英矿池
    ELITE_V2(4000, 5200, 45),
    // 超级矿池
    SUPER_V2(100000, 13500, 45)
    ;
    double cost;
    double output;
    int period;

    PoolType(double cost, double output, int period){
        this.cost = cost;
        this.output = output;
        this.period = period;
    }
}
