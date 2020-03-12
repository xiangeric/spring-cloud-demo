package org.example.stream;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

@Slf4j
@EnableBinding(Source.class)
public class MessageSender {

    //消息发送通道
    @Autowired
    private MessageChannel output;

    public boolean send(Student student){
        Message<Student> message = MessageBuilder.withPayload(student).build();
        log.info("MessageProvider:::send "+student);
        boolean result = output.send(message);
        return result;
    }
}
