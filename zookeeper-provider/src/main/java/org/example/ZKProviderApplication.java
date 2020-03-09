package org.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ZKProviderApplication
{
    public static void main( String[] args ) {
        SpringApplication.run(ZKProviderApplication.class);
    }
}
