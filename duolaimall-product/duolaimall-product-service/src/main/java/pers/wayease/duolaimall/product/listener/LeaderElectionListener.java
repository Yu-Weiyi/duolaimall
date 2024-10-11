package pers.wayease.duolaimall.product.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.integration.leader.event.AbstractLeaderEvent;
import org.springframework.integration.leader.event.OnGrantedEvent;
import org.springframework.stereotype.Component;
import pers.wayease.duolaimall.product.initializer.SkuBloomFilterInitializer;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.listener
 * @name LeaderElectionListener
 * @description Leader election listener class.
 * @since 2024-10-11 11:41
 */
@Component
@Slf4j
public class LeaderElectionListener implements ApplicationListener {

    @Autowired
    private SkuBloomFilterInitializer skuBloomFilterInitializer;

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof AbstractLeaderEvent) {
            AbstractLeaderEvent abstractLeaderEvent = (AbstractLeaderEvent) applicationEvent;
            log.info("Leader election event: {}, Source: {}, Role: {}, Context: {}",
                    abstractLeaderEvent, abstractLeaderEvent.getSource(), abstractLeaderEvent.getRole(), abstractLeaderEvent.getContext()
            );

            if (applicationEvent instanceof OnGrantedEvent) {
                OnGrantedEvent onGrantedEvent = (OnGrantedEvent) applicationEvent;
                log.info("Leader role granted.");

                // initializer(s)
                skuBloomFilterInitializer.initialize();
                log.info("All initializer done.");
            }
        }
    }
}
