package org.example.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.Future;

@Service
@Slf4j
public class HystrixCollapserService {

    private static String PROVIDER_URL = "http://EUREKA-PROVIDER/students";

    private final String GET_STUDENTS_URL = PROVIDER_URL + "/getStudents";

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCollapser(batchMethod = "getStudents",
            collapserProperties = {@HystrixProperty(name = "timerDelayInMilliseconds",value = "100")})
    public Future<Student> getStudentById(Integer id){
        //该方法体并不会进入
        return null;
    }

    /**
     *
     * @param ids 与getStudentById(Integer id)相呼应，其泛型均为Integer,
     *            该参数必须为List
     * @return 合并之后的响应值，注意，这里需要保证返回的List的size必须与调用次数保持一致,
     *         即使没有返回值，也需要将null填充进去
     */
    @HystrixCommand
    public List<Student> getStudents(List<Integer> ids){
        log.info("ids.size="+ids.size());
        HttpEntity<List<Integer>> requestEntity = new HttpEntity<>(ids);
        List<Student> result =
                restTemplate.exchange(
                        GET_STUDENTS_URL,
                        HttpMethod.POST,
                        requestEntity,
                        new ParameterizedTypeReference<List<Student>>() {}).getBody();
        log.info("result.size="+result.size());
        return result;
    }

}
