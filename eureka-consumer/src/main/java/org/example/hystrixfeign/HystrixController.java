package org.example.hystrixfeign;

import org.example.entity.Resp;
import org.example.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/hystrix/students")
public class HystrixController {


    @Autowired
    private HFStudentService studentService;

    @GetMapping
    public Set<Student> get(){
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student get(@PathVariable Integer id){
        return studentService.getStudentById(id);
    }


    @PostMapping
    public Resp add(@RequestBody Student student){
        return studentService.add(student);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        studentService.delete(id);
    }

    @PatchMapping("/{id}")
    public Resp update(@PathVariable Integer id,@RequestBody Student student){
        return studentService.update(id,student);
    }


}
