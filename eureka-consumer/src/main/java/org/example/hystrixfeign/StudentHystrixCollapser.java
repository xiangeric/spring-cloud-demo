package org.example.hystrixfeign;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Student;

import java.util.Collection;
import java.util.List;

@Slf4j
public class StudentHystrixCollapser extends HystrixCollapser<List<Student>,Student,Integer> {

    private Integer id;

    private final static Setter setter =
            Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("studentHystrixCollapser"))
            .andCollapserPropertiesDefaults(
                    HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(100));

    public StudentHystrixCollapser(Integer id){
        super(setter);
        this.id = id;
    }

    @Override
    public Integer getRequestArgument() {
        log.info("getRequestArgument....");
        return this.id;
    }

    @Override
    protected HystrixCommand<List<Student>> createCommand(Collection<CollapsedRequest<Student, Integer>> collapsedRequests) {
        log.info("createCommand....");
        return new StudentHystrixCommand(collapsedRequests);
    }


    @Override
    protected void mapResponseToRequests(List<Student> batchResponse, Collection<CollapsedRequest<Student, Integer>> collapsedRequests) {
        log.info("mapResponseToRequests....");
        int index=0;
        for(CollapsedRequest request:collapsedRequests){
            request.setResponse(batchResponse.get(index++));
        }
    }
}
