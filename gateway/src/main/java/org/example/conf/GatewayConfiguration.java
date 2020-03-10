package org.example.conf;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//@Configuration
public class GatewayConfiguration {

    //@Bean
    public RouteLocator providerRouteLocator(RouteLocatorBuilder builder){
        RouteLocatorBuilder.Builder routes = builder.routes();
        //设置id以及predicates
        return routes.route("consul-provider",
                r -> r.path("/provider-api/**")
                        .uri("http://127.0.0.1:18087")
        ).build();
    }


}
