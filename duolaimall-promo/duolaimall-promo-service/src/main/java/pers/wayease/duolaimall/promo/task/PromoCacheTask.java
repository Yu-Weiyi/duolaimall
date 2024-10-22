package pers.wayease.duolaimall.promo.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.wayease.duolaimall.promo.service.PromoService;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.promo.task
 * @name PromoTask
 * @description Promo cache task class.
 * @since 2024-10-21 03:22
 */
@Component
@Slf4j
public class PromoCacheTask {

    @Autowired
    PromoService promoService;

//    @Scheduled(cron = "0 0 2 * * ?")
//    @Scheduled(cron = "0/10 * * * * ?")

    public void initCache() {
        log.info("cache task start");
        promoService.importIntoRedis();
        log.info("cache task done");
    }

    public void clearCache() {
        log.info("clear task start");
        promoService.clearRedisCache();
        log.info("clear task done");
    }
}
