package org.example;

import org.example.entity.Student;
import org.example.ribbon.SpringRibbonConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 * 集成测试Zuul访问，利用http://{zuul.application.name}/{eureka.application.name}/{url},
 * 该测试需要保证zuul server已经注册到eureka server中
 */
@SpringBootTest(classes = SpringRibbonConfiguration.class)
@RunWith(SpringRunner.class)
public class ZuulTest {

    @Autowired
    public RestTemplate restTemplate;

    private static String PROVIDER_URL = "http://ZUUL-SERVER/myzuul/provider-api/students/{1}?token=123";

    @Test
    public void test(){
        Student student = restTemplate.getForEntity(PROVIDER_URL,Student.class,1).getBody();
        System.out.println(student);
    }
}
