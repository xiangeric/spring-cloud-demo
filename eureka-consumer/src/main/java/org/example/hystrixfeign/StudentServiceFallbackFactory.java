package org.example.hystrixfeign;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Resp;
import org.example.entity.Student;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

@Component
@Slf4j
public class StudentServiceFallbackFactory implements FallbackFactory<HFStudentService> {

    @Override
    public HFStudentService create(Throwable throwable) {
        return new HFStudentService() {
            @Override
            public Set<Student> getAllStudents() {
                log.info("StudentServiceFallbackFactory:::getAllStudents");
                return Collections.emptySet();
            }

            @Override
            public Student getStudentById(Integer id) {
                log.info("StudentServiceFallbackFactory:::getStudentById");
                return new Student(Integer.MAX_VALUE,"unknownStudent",Integer.MAX_VALUE);
            }

            @Override
            public Resp add(Student student) {
                log.info("StudentServiceFallbackFactory:::add");
                return Resp.ofFail("There is error occurs when invoke  add()");
            }

            @Override
            public void delete(Integer id) {
                log.info("StudentServiceFallbackFactory:::delete");
                return;
            }

            @Override
            public Resp update(Integer id, Student student) {
                log.info("StudentServiceFallbackFactory:::update "+id+","+student);
                return Resp.ofFail("There is error occurs when invoke  update()");
            }
        };
    }
}
