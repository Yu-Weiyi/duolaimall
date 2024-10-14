package pers.wayease.duolaimall.common.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pers.wayease.duolaimall.common.interceptor.GlobalThreadLocalContextRemoveInterceptor;
import pers.wayease.duolaimall.common.interceptor.RequestHeaderInterceptor;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.common.configuration
 * @name WebMvcConfiguration
 * @description Web MVC configuration class.
 * @since 2024-10-13 15:12
 */
@Configuration
@Slf4j
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private RequestHeaderInterceptor requestHeaderInterceptor;
    @Autowired
    private GlobalThreadLocalContextRemoveInterceptor globalThreadLocalContextRemoveInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(requestHeaderInterceptor)
                .addPathPatterns("/user/logout/**");
        interceptorRegistry.addInterceptor(globalThreadLocalContextRemoveInterceptor)
                .addPathPatterns("/**");
        log.info("Interceptor registered.");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {
//        resourceHandlerRegistry
//                .addResourceHandler("doc.html")// knife4j
//                .addResourceLocations("classpath:/META-INF/resources/");
        resourceHandlerRegistry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        log.info("Resource handler registered.");
    }
}
