package org.example.feign;

import org.rule.RibbonConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@RibbonClient(name="EUREKA-PROVIDER",configuration = RibbonConfiguration.class) //可以直接使用自定义负载均衡规则
public class MyFeignConfiguration {
}
