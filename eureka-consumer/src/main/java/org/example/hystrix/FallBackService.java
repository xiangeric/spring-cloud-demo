package org.example.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class FallBackService{

    @Autowired
    private RestTemplate restTemplate;

    private static String PROVIDER_URL = "http://EUREKA-PROVIDER/students/{1}";

    @HystrixCommand(commandKey = "fallBackKey",fallbackMethod = "fallbackMethod")
    public Student getStudentById(Integer id){
        log.info("getStudentById....");
        return restTemplate.getForEntity(PROVIDER_URL,Student.class,id).getBody();

    }

    public Student fallbackMethod(Integer id){
        log.info("fallbackMethod...["+id+"]");
        return new Student(Integer.MAX_VALUE,"unknownStudent",Integer.MAX_VALUE);
    }


    @HystrixCommand(commandKey = "circuitBreakerKey",
            fallbackMethod = "circuitFallbackMethod",
            commandProperties = {
                @HystrixProperty(name = "circuitBreaker.enabled",value="TRUE"),
                @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value="5"),
                @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value="10"),
                @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value="4000")
            })
    public String circuitBreakerMethod(int index) throws Exception{
        if(index < 5) {	// 模拟成功
            return String.valueOf(index);
        } else if(index<10){
            throw new Exception();
        }else{
            return String.valueOf(index);
        }
    }

    public String circuitFallbackMethod(int index) throws Exception{
        return "index=["+index+"] getFallback";
    }
}
