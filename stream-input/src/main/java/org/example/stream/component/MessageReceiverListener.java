package org.example.stream.component;


import lombok.extern.slf4j.Slf4j;
import org.example.entity.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;

@Slf4j
@EnableBinding(Sink.class)
public class MessageReceiverListener {

    @Value("${server.port}")
    private int serverPort;

    @StreamListener(Sink.INPUT)
    public void receive(Message<Student> message){
        Student student = message.getPayload();
        log.info("MessageReceiverListener::: receive "+student+"; port="+serverPort);
    }
}
