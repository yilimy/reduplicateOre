package com.orice.io.btd.strategy;

import com.orice.io.btd.Business;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * BTD变更策略
 *
 * @author caimeng
 * @date 2020/3/10 15:24
 */
@Slf4j
@Data
@SuperBuilder
public class SaleStrategy extends InvestStrategy {
    /** 变更的BTD数量 **/
    private double btd;

    @Override
    public void deal(Business business) {
        business.enCash(btd);
        log.info("enCash {} on {}", btd, date);
    }
}
