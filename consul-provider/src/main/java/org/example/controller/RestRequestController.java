package org.example.controller;

import org.example.entity.Resp;
import org.example.entity.Student;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@RestController
@RequestMapping("/students")
public class RestRequestController {

    public static Map<Integer, Student> dataMap = new ConcurrentHashMap<>();

    static {
        dataMap.put(1, new Student(1,"Tony",20));
        dataMap.put(2, new Student(2,"Jenny",19));
        dataMap.put(3,new Student(3,"Thomas",21));
    }

    @GetMapping
    public Set<Student> get(){
        Set<Student> students = new HashSet<>(dataMap.size());
        dataMap.values().forEach(students::add);
        return students;
    }

    @GetMapping("/{id}")
    public Student get(@PathVariable Integer id){
        return dataMap.get(id);
    }


    @PostMapping("/getStudents")
    public List<Student> getStudents(@RequestBody List<Integer> ids){
        if(ids!=null && !ids.isEmpty()){
            List<Student> list = new ArrayList<>(ids.size());
            for(Integer id:ids){
                list.add(dataMap.get(id));
            }
            return list;
        }
        return Collections.emptyList();
    }

    @PostMapping
    public Resp add(@RequestBody Student student){
        if(student == null){
            return Resp.ofFail("Data can not be null");
        }else if(student.getId() == null){
            return Resp.ofFail("id can not be null");
        }else{
            if(dataMap.containsKey(student.getId())){
                String message = "id["+student.getId()+"] is existing already";
                return Resp.ofFail(message);
            }
            dataMap.put(student.getId(),student);
            return Resp.ofSuccess(null);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        dataMap.remove(id);
    }

    @PatchMapping("/{id}")
    public Resp update(@PathVariable Integer id,@RequestBody Student student){
        if(id == null){
            return Resp.ofFail("id can not be null");
        }else if(!dataMap.containsKey(id)){
            String message = "id["+student.getId()+"] is not exist";
            return Resp.ofFail(message);
        }else if(id!=student.getId()){
            return Resp.ofFail("id can not be updated");
        }else{
            dataMap.put(id,student);
            return Resp.ofSuccess(null);
        }
    }
}
