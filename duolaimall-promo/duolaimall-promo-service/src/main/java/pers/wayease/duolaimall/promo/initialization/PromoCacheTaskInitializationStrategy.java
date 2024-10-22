package pers.wayease.duolaimall.promo.initialization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import pers.wayease.duolaimall.common.initialization.InitializationStrategy;
import pers.wayease.duolaimall.promo.task.PromoCacheTask;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.promo.initialization
 * @name PromoCacheTaskInitializationStrategy
 * @description Promo cache task initialization strategy class.
 * @since 2024-10-21 06:32
 */
@Component
@Slf4j
public class PromoCacheTaskInitializationStrategy implements InitializationStrategy {

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private PromoCacheTask promoCacheTask;

    @Override
    public void initialize() {
        taskScheduler.schedule(
                promoCacheTask::initCache,
                new CronTrigger("0 0 4 * * ?")
        );
        log.info("Cron task promoCacheTask::initCache registered.");

        taskScheduler.schedule(
                promoCacheTask::clearCache,
                new CronTrigger("0 0 22 * * ?")
        );
        log.info("Cron task promoCacheTask::clearCache registered.");
    }
}
