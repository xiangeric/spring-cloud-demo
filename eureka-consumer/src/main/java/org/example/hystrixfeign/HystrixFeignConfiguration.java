package org.example.hystrixfeign;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@EnableHystrix
public class HystrixFeignConfiguration {


}
