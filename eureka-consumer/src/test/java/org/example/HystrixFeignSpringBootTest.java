package org.example;

import org.example.entity.Student;
import org.example.hystrixfeign.HFStudentService;
import org.example.hystrixfeign.HystrixFeignConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(classes = HystrixFeignConfiguration.class)
@RunWith(SpringRunner.class)
public class HystrixFeignSpringBootTest {

    @Autowired
    private HFStudentService studentService;

    /**
     * 测试Hystrix与Feign整合
     */
    @Test
    public void test(){
        Student student = studentService.getStudentById(1);
        System.out.println(student);
    }
}
