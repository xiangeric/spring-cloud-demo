package org.example.stream.component;

import org.example.stream.channel.DlqSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(DlqSource.class)
public class DlqSender {

    @Autowired
    @Qualifier(DlqSource.OUTPUT)
    private MessageChannel channel;

    public void send(String msg){
        Message<String> message = MessageBuilder.withPayload(msg).build();
        channel.send(message);
    }
}
