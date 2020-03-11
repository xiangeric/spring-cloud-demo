package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/rest")
@RefreshScope
public class RestClientController {

    @Value("${server.port}")
    private Integer serverPort;

    @Value("${spring.profiles}")
    private String springProfiles;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${custom.property}")
    private String customProperty;

    @GetMapping
    public Map<String,String> applicationConf(){
        Map<String,String> data = new HashMap<>();
        data.put("server.port",String.valueOf(serverPort));
        data.put("spring.profiles",springProfiles);
        data.put("spring.application.name",applicationName);
        data.put("custom.property",customProperty);
        return data;
    }
}
