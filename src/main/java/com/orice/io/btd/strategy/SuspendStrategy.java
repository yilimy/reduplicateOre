package com.orice.io.btd.strategy;

import com.orice.io.btd.Business;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author caimeng
 * @date 2020/3/22 16:21
 */
@Data
@SuperBuilder
@Slf4j
public class SuspendStrategy extends InvestStrategy {
    /** 是否暂停复投 **/
    private boolean suspended;

    @Override
    public void deal(Business business) {
        if (suspended){
            log.info("暂停自动复投 ... ");
            business.suspend();
        } else {
            log.info("开启自动复投 ... ");
            business.reAuto();
        }
    }
}
