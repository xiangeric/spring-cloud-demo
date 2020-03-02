package org.example.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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
}
