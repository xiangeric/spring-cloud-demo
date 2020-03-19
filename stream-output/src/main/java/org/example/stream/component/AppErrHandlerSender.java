package org.example.stream.component;

import org.example.stream.channel.AppErrHandlerSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(AppErrHandlerSource.class)
public class AppErrHandlerSender {

    @Autowired
    @Qualifier(AppErrHandlerSource.OUTPUT)
    private MessageChannel messageChannel;

    public void send(String msg){
        Message<String> message = MessageBuilder.withPayload(msg).build();
        messageChannel.send(message);
    }
}
