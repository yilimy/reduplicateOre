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
    HIGH_V2(10000, 12800, 45),
    // 精英矿池
    ELITE_V2(40000, 52000, 45),
    // 超级矿池
    SUPER_V2(100000, 135000, 45),

    // 体验矿池
    EXPERIENCE2(50, 54, 30)
    ;
    double cost;
    double output;
    int period;

    PoolType(double cost, double output, int period){
        this.cost = cost;
        this.output = output;
        this.period = period;
    }

    public static PoolType matches(String name){
        for (PoolType type : values()){
            if (type.name().equalsIgnoreCase(name)){
                return type;
            }
        }
        return null;
    }
}
