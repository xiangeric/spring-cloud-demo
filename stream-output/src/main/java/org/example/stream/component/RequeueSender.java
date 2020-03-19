package org.example.stream.component;

import lombok.extern.slf4j.Slf4j;
import org.example.stream.channel.RequeueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(RequeueSource.class)
@Slf4j
public class RequeueSender {

    @Autowired
    @Qualifier(RequeueSource.REQUEUE_OUTPUT)
    private MessageChannel messageChannel;

    public void send(String msg){
        Message<String> message = MessageBuilder.withPayload(msg).build();
        messageChannel.send(message);
    }


}
