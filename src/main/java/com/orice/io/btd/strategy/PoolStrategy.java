package com.orice.io.btd.strategy;

import com.orice.io.btd.Business;
import com.orice.io.btd.bean.PoolType;
import com.orice.io.btd.util.DateUtil;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * BTD矿池投入策略
 *
 * @author caimeng
 * @date 2020/3/10 15:33
 */
@Data
@SuperBuilder
public class PoolStrategy extends InvestStrategy{
    /** 复投产品 **/
    private PoolType type;

    @Override
    public void deal(Business business) {
        business.addPool(type, date);
    }
}
