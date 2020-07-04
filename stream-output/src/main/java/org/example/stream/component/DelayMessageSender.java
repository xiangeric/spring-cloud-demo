package org.example.stream.component;

import lombok.extern.slf4j.Slf4j;
import org.example.stream.channel.DelaySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(DelaySource.class)
@Slf4j
public class DelayMessageSender {

    @Autowired
    @Qualifier(DelaySource.DELAY_OUTPUT)
    private MessageChannel output;

    public void send(String originMessage){
        Message<String> message =
                MessageBuilder.withPayload(originMessage)
                        .setHeader("x-delay", 5000)
                        .build();
        output.send(message);
    }
}
