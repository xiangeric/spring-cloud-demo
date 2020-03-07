package org.example.hystrixfeign;

import org.example.entity.Resp;
import org.example.entity.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@FeignClient(value = "EUREKA-PROVIDER",fallbackFactory = StudentServiceFallbackFactory.class)
@RequestMapping("/students")
public interface HFStudentService {

    @GetMapping
    public Set<Student> getAllStudents();

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable("id") Integer id);

    @PostMapping
    public Resp add(@RequestBody Student student);

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id);

    @PatchMapping("/{id}")
    public Resp update(@PathVariable("id") Integer id, @RequestBody Student student);

}
