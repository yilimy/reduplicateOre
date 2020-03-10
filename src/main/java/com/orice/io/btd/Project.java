package com.orice.io.btd;

import com.orice.io.btd.strategy.InvestStrategy;
import com.orice.io.btd.strategy.PoolStrategy;
import com.orice.io.btd.strategy.SaleStrategy;
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
        project.start(
                LocalDate.parse("2020-02-09"),
                LocalDate.parse("2020-03-11")
        );
    }

    /**
     * 初始化策略
     */
    private void init(){
        // 初始BTD总数
        double intBtd = 408.666355;
        business = new Business(PoolType.SUPER_V1.cost + intBtd);
        // 设置购买矿池的时间、种类
        strategies.add(createStrategy("2020-02-09", PoolType.SUPER_V1));
        strategies.add(createStrategy("2020-02-10", PoolType.UPGRADE_V1));
        strategies.add(createStrategy("2020-02-10", PoolType.JUNIOR_V1));
        strategies.add(createStrategy("2020-02-10", PoolType.JUNIOR_V1));
        strategies.add(createStrategy("2020-02-10", PoolType.JUNIOR_V1));
        strategies.add(createStrategy("2020-02-10", PoolType.JUNIOR_V1));
        strategies.add(createStrategy("2020-02-10", PoolType.JUNIOR_V1));
        strategies.add(createStrategy("2020-02-11", PoolType.MIDDLE_V1));
        strategies.add(createStrategy("2020-02-11", PoolType.MIDDLE_V1));
        strategies.add(createStrategy("2020-02-11", PoolType.MIDDLE_V1));
        strategies.add(createStrategy("2020-02-11", PoolType.MIDDLE_V1));
        strategies.add(createStrategy("2020-02-11", PoolType.JUNIOR_V1));
        strategies.add(createStrategy("2020-02-11", PoolType.JUNIOR_V1));
        strategies.add(createStrategy("2020-02-11", PoolType.JUNIOR_V1));
        strategies.add(createStrategy("2020-02-13", PoolType.HIGH_V1));
        strategies.add(createStrategy("2020-02-15", PoolType.HIGH_V1));
        strategies.add(createStrategy("2020-02-22", PoolType.ELITE_V1));

        strategies.add(createStrategy("2020-02-26", 26667));

        strategies.add(createStrategy("2020-03-10", PoolType.SUPER_V2));
    }

    private InvestStrategy createStrategy(String dateStr, PoolType type){
        return createStrategy(dateStr, type, 0);
    }

    private InvestStrategy createStrategy(String dateStr, double btd){
        return createStrategy(dateStr, null, btd);
    }

    private InvestStrategy createStrategy(String dateStr, PoolType type, double btd){
        if (type != null){
            return PoolStrategy.builder().type(type).date(LocalDate.parse(dateStr)).build();
        }
        return SaleStrategy.builder().btd(btd).date(LocalDate.parse(dateStr)).build();
    }

    /**
     * 开始复投 [start, end)
     * @param start
     * @param end
     */
    private void start(LocalDate start, LocalDate end){
        for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)){
            System.out.println("");
            log.info("date : {}", date);
            processDate(date);
        }
    }

    /**
     * 检查每天的复投
     * @param localDate
     */
    private void processDate(LocalDate localDate) {
        Date date = DateUtil.localDate2Date(localDate);

        // 核算前一天收益
        business.profit(date);

        // 检查到指定日期有策略，执行策略
        strategies.stream().forEach(item -> {
            if (localDate.isEqual(item.getDate())){
                log.info("check strategy {}", item);
                item.deal(business);
            }
        });

        // 触发自动购买策略，10w, 4w

        // 当日结算
        System.out.printf("%s total btd %s\n", localDate, business.holds());
    }

}
