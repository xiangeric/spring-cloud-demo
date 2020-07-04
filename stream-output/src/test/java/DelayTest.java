import org.example.StreamProviderApplication;
import org.example.stream.component.DelayMessageSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = StreamProviderApplication.class)
@RunWith(SpringRunner.class)
public class DelayTest {

    @Autowired
    private DelayMessageSender sender;

    @Test
    public void test(){
        sender.send("delay message!!!");
    }
}
