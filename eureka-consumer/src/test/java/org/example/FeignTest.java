package org.example;

import org.example.entity.Student;
import org.example.feign.MyFeignConfiguration;
import org.example.feign.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * 测试Feign客户端的使用
 */
@SpringBootTest(classes = MyFeignConfiguration.class)
@RunWith(SpringRunner.class)
public class FeignTest {

    @Autowired
    private StudentService service;

    @Test
    public void test(){
        Set<Student> students = service.getAllStudents();
        System.out.println(students);
        Student student = service.getStudentById(1);
        System.out.println(student);
        Student jenny = new Student(10,"Jenny",29);
        service.add(jenny);
        jenny.setAge(28);
        service.update(jenny.getId(),jenny);
        service.delete(1);
        students = service.getAllStudents();
        System.out.println(student);

    }
}
