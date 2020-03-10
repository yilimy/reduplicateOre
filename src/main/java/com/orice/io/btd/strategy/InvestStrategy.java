package com.orice.io.btd.strategy;

import com.orice.io.btd.Business;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * @author caimeng
 * @date 2020/2/23 21:26
 */
@Data
@SuperBuilder
public abstract class InvestStrategy {
    /** 复投日期(次日生效) **/
    protected LocalDate date;

    /**
     * 处理策略
     * @param business
     */
    public abstract void deal(Business business);
}
