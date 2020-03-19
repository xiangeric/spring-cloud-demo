import org.example.StreamProviderApplication;
import org.example.entity.Student;
import org.example.stream.component.MessageSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@SpringBootTest(classes = StreamProviderApplication.class)
@RunWith(SpringRunner.class)
public class StreamProviderTest {

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private ApplicationContext applicationContext;


    @Test
    public void getChannel(){
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        Object bean;
        for(String name:beanDefinitionNames){
             bean = applicationContext.getBean(name);
             if(bean instanceof MessageChannel){
                 System.out.println(name+":"+bean);
             }

        }

    }





    @Test
    public void test(){

        Student student;
        for(int i=0;i<5;i++){
            student = new Student(1, UUID.randomUUID().toString(),20+i);
            messageSender.send(student);
        }
    }
}
