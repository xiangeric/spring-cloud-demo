package org.example.stream.component;

import lombok.extern.slf4j.Slf4j;
import org.example.stream.channel.DelaySink;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

@EnableBinding(DelaySink.class)
@Slf4j
public class DelayMessageReceiver {

    @StreamListener(DelaySink.DELAY_INPUT)
    public void handle(Message<String> message){
        log.info(message.getPayload());
    }
}
