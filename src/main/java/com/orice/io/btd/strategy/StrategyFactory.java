package com.orice.io.btd.strategy;

import com.orice.io.btd.bean.PoolType;

import java.time.LocalDate;

/**
 * @author caimeng
 * @date 2020/3/10 18:22
 */
public class StrategyFactory {

    /**
     *
     * @param buyDate 购买日期
     * @param type
     * @return
     */
    public static InvestStrategy createStrategy(String buyDate, PoolType type){
        return createStrategy(buyDate, type, 0);
    }

    /**
     *
     * @param enCashDate 取现日期
     * @param btd
     * @return
     */
    public static InvestStrategy createStrategy(String enCashDate, double btd){
        return createStrategy(enCashDate, null, btd);
    }

    private static InvestStrategy createStrategy(String dateStr, PoolType type, double btd){
        if (type != null){
            return PoolStrategy.builder().type(type).date(LocalDate.parse(dateStr)).build();
        }
        return SaleStrategy.builder().btd(btd).date(LocalDate.parse(dateStr)).build();
    }
}
