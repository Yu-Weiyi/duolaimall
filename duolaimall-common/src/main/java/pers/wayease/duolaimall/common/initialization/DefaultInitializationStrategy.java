package pers.wayease.duolaimall.common.initialization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.common.initialization
 * @name DefaultInitializationStrategy
 * @description Default initialization strategy class.
 * @since 2024-10-13 21:21
 */
@Component
@Slf4j
public class DefaultInitializationStrategy implements InitializationStrategy {

    // do nothing
    @Override
    public void initialize() {
        log.info("Default initialization strategy.");
    }
}
