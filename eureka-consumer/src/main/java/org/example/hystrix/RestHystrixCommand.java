package org.example.hystrix;

import com.netflix.hystrix.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Resp;
import org.example.entity.Student;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class RestHystrixCommand extends HystrixCommand<Resp> {

    private RestTemplate restTemplate;

    private static String PROVIDER_URL = "http://localhost:18081/students/";

    private Student student;

    public RestHystrixCommand(Setter setter,RestTemplate restTemplate,Student student){
        super(setter);
        this.restTemplate = restTemplate;
        this.student = student;
    }

    @Override
    protected Resp run() throws Exception {
        Resp resp = restTemplate.postForEntity(PROVIDER_URL, student,Resp.class).getBody();
        return resp;
    }

    @Override
    protected Resp getFallback() {
        log.info("RestHystrixCommand:::getFallback");
        return Resp.ofFail("can not add the student");
    }
}
