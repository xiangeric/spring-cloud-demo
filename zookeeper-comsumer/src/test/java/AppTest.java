import org.example.ZKComsumerApplication;
import org.example.entity.Resp;
import org.example.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootTest(classes = ZKComsumerApplication.class)
@RunWith(SpringRunner.class)
public class AppTest {

    @Autowired
    public RestTemplate restTemplate;

    private static String PROVIDER_URL = "http://zookeeper-provider/students";

    private static String URL = PROVIDER_URL+"/{1}";

    @Test
    public void testProvider(){
        System.out.println("=== get student whose id is 1");
        Student student = restTemplate.getForEntity(URL,Student.class,1).getBody();
        System.out.println(student);

        System.out.println("=== add student whose id is 4");
        Student student4 = new Student(4,"Jeff",22);
        Resp postResp = restTemplate.postForEntity(PROVIDER_URL, student4,Resp.class).getBody();
        System.out.println(postResp);

        System.out.println("====== update student whose id is 1");
        student.setAge(30);
        Resp patchResp = restTemplate.patchForObject(URL,student, Resp.class,1);
        System.out.println(patchResp);

        System.out.println("=== delete student whose id is 2");
        restTemplate.delete(URL,2);


        System.out.println("=== get all students");
        List<Student> list =
                restTemplate.exchange(
                        PROVIDER_URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Student>>() {}).getBody();
        System.out.println(list);
    }



}
