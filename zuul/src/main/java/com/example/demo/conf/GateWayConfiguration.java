package com.example.demo.conf;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * 网关配置类
 */
@Configuration
public class GateWayConfiguration {


    @Bean
    public FilterRegistrationBean corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        //支持cookie跨域
        config.setAllowCredentials(true);
        //配置允许跨域访问的域名，这里*表示全部
        config.setAllowedOrigins(Arrays.asList("*"));
        //设置允许的头
        config.setAllowedHeaders(Arrays.asList("*"));
        //设置允许跨域的方法，GET,POST....,这里表示允许全部
        config.setAllowedMethods(Arrays.asList("*"));
        //缓存时间，在指定的时间内，对于相同的请求就不需要再次检查了
        config.setMaxAge(300l);
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }


}
