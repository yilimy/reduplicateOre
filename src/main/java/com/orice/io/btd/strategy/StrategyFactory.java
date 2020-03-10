package com.orice.io.btd.strategy;

import com.orice.io.btd.bean.PoolType;

import java.time.LocalDate;

/**
 * @author caimeng
 * @date 2020/3/10 18:22
 */
public class StrategyFactory {

    public static InvestStrategy createStrategy(String dateStr, PoolType type){
        return createStrategy(dateStr, type, 0);
    }

    public static InvestStrategy createStrategy(String dateStr, double btd){
        return createStrategy(dateStr, null, btd);
    }

    public static InvestStrategy createStrategy(String dateStr, PoolType type, double btd){
        if (type != null){
            return PoolStrategy.builder().type(type).date(LocalDate.parse(dateStr)).build();
        }
        return SaleStrategy.builder().btd(btd).date(LocalDate.parse(dateStr)).build();
    }
}
