package org.example.hystrix;

import org.example.common.RTConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Import;

@EnableEurekaClient
@SpringBootApplication
@Import(RTConfiguration.class)
@EnableHystrix
public class MyHystrixConfiguration {

}
