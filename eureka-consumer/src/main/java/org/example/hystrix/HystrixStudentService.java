package org.example.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Resp;
import org.example.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class HystrixStudentService {

    @Autowired
    private RestTemplate restTemplate;

    private static String PROVIDER_URL = "http://EUREKA-PROVIDER/students";

    private static String URL = PROVIDER_URL+"/{1}";

    @HystrixCommand(commandKey = "commondKey")
    public Student getStudentById(@CacheKey Integer id){
        Student student = restTemplate.getForEntity(URL,Student.class,id).getBody();
        log.info("getStudentById...");
        return student;
    }

    @HystrixCommand(commandKey = "commondKey")
    public Resp addStudent(Student student){
        Resp resp = restTemplate.postForEntity(PROVIDER_URL, student,Resp.class).getBody();
        return resp;
    }

    @HystrixCommand(commandKey = "commondKey")
    @CacheRemove(commandKey = "commondKey")
    public Resp updateStudentById(@CacheKey Integer id,Student student){
        Resp resp = restTemplate.patchForObject(URL,student,Resp.class,id);
        log.info("updateStudentById...");
        return resp;
    }


    @CacheRemove(commandKey = "commondKey", cacheKeyMethod = "getCacheKey")
    @HystrixCommand
    public void deleteStudentById(Integer id){
        restTemplate.delete(URL,id);
    }




}
