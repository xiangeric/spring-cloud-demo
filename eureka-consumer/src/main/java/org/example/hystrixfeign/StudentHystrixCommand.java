package org.example.hystrixfeign;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import org.example.entity.Student;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StudentHystrixCommand extends HystrixCommand<List<Student>> {

    private final static String PROVIDER_URL = "http://localhost:18081/students";

    private final static Setter setter =
            Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("Student"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("StudentHystrixCommand"));

    private Collection<HystrixCollapser.CollapsedRequest<Student, Integer>> collapsedRequests;

    private RestTemplate restTemplate = new RestTemplate();

    public StudentHystrixCommand(Collection<HystrixCollapser.CollapsedRequest<Student, Integer>> collapsedRequests){
        super(setter);
        this.collapsedRequests = collapsedRequests;
    }


    @Override
    protected List<Student> run() throws Exception {
        List<Integer> ids = new ArrayList<>(collapsedRequests.size());
        for(HystrixCollapser.CollapsedRequest<Student, Integer> request:collapsedRequests){
            ids.add(request.getArgument());
        }
        final String GET_STUDENTS_URL = PROVIDER_URL + "/getStudents";
        HttpEntity<List<Integer>> requestEntity = new HttpEntity<>(ids);
        List<Student> result =
                restTemplate.exchange(
                        GET_STUDENTS_URL,
                        HttpMethod.POST,
                        requestEntity,
                        new ParameterizedTypeReference<List<Student>>() {}).getBody();
        return result;
    }

}
