import org.example.StreamProviderApplication;
import org.example.entity.Student;
import org.example.stream.MessageSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@SpringBootTest(classes = StreamProviderApplication.class)
@RunWith(SpringRunner.class)
public class StreamProviderTest {

    @Autowired
    private MessageSender messageSender;


    @Test
    public void test(){

        Student student;
        for(int i=0;i<5;i++){
            student = new Student(1, UUID.randomUUID().toString(),20+i);
            messageSender.send(student);
        }
    }
}
