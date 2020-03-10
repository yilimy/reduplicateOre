package com.orice.io.btd;

import com.orice.io.btd.util.DateUtil;
import lombok.Builder;
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
        business.addPool(type, DateUtil.localDate2Date(date));
    }
}
