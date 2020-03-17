import org.example.StreamProviderApplication;
import org.example.stream.StreamSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = StreamProviderApplication.class)
@RunWith(SpringRunner.class)
public class StreamTest {

    @Autowired
    private StreamSender streamSender;
//
    @Test
    public void test(){
        String message = "message%s";
        for(int i=0;i<1;i++){
            streamSender.send(String.format(message,i));
        }
    }
}
