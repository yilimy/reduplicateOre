package com.orice.io.btd;

import com.orice.io.btd.bean.PoolType;
import com.orice.io.btd.strategy.InvestStrategy;
import com.orice.io.btd.strategy.PoolStrategy;
import com.orice.io.btd.strategy.SaleStrategy;
import com.orice.io.btd.strategy.StrategyFactory;
import com.orice.io.btd.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 业务线
 * 
 * @author caimeng
 * @date 2020/2/23 21:11
 */
@Slf4j
public class Project {

    private List<InvestStrategy> strategies = new ArrayList<>();
    private Business business;

    public static void main(String[] args) {
        Project project = new Project();
        project.init();
        // 03-10续期10w，03-11生效，04-24截止：[02-09,04-25)
        project.business.start(
                LocalDate.parse("2020-02-09"),
                LocalDate.parse("2020-05-25")
        );
    }

    /**
     * 初始化策略
     */
    private void init(){
        // 初始BTD总数
        double intBtd = 408.666355;
        business = new Business(PoolType.SUPER_V1.getCost() + intBtd);
        // 设置购买矿池的时间、种类
        strategies.add(StrategyFactory.createStrategy("2020-02-09", PoolType.SUPER_V1));
        strategies.add(StrategyFactory.createStrategy("2020-02-10", PoolType.UPGRADE_V1));
        strategies.add(StrategyFactory.createStrategy("2020-02-10", PoolType.JUNIOR_V1));
        strategies.add(StrategyFactory.createStrategy("2020-02-10", PoolType.JUNIOR_V1));
        strategies.add(StrategyFactory.createStrategy("2020-02-10", PoolType.JUNIOR_V1));
        strategies.add(StrategyFactory.createStrategy("2020-02-10", PoolType.JUNIOR_V1));
        strategies.add(StrategyFactory.createStrategy("2020-02-10", PoolType.JUNIOR_V1));
        strategies.add(StrategyFactory.createStrategy("2020-02-11", PoolType.MIDDLE_V1));
        strategies.add(StrategyFactory.createStrategy("2020-02-11", PoolType.MIDDLE_V1));
        strategies.add(StrategyFactory.createStrategy("2020-02-11", PoolType.MIDDLE_V1));
        strategies.add(StrategyFactory.createStrategy("2020-02-11", PoolType.MIDDLE_V1));
        strategies.add(StrategyFactory.createStrategy("2020-02-11", PoolType.JUNIOR_V1));
        strategies.add(StrategyFactory.createStrategy("2020-02-11", PoolType.JUNIOR_V1));
        strategies.add(StrategyFactory.createStrategy("2020-02-11", PoolType.JUNIOR_V1));
        strategies.add(StrategyFactory.createStrategy("2020-02-13", PoolType.HIGH_V1));
        strategies.add(StrategyFactory.createStrategy("2020-02-15", PoolType.HIGH_V1));
        strategies.add(StrategyFactory.createStrategy("2020-02-22", PoolType.ELITE_V1));

        strategies.add(StrategyFactory.createStrategy("2020-02-26", 26667));


        strategies.add(StrategyFactory.createStrategy("2020-04-02", 53334));

        business.setStrategies(strategies);
    }



}
