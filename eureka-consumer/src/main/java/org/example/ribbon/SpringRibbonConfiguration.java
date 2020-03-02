package org.example.ribbon;

import org.example.common.RTConfiguration;
import org.rule.RibbonConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Import;


@RibbonClient(name="EUREKA-PROVIDER",configuration = RibbonConfiguration.class)
@EnableEurekaClient
@SpringBootApplication
@Import(RTConfiguration.class)
public class SpringRibbonConfiguration {}
