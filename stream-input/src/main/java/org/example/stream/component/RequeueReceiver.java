package org.example.stream.component;

import lombok.extern.slf4j.Slf4j;
import org.example.stream.channel.RequeueSink;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(RequeueSink.class)
@Slf4j
public class RequeueReceiver {

    private int times = 0;

    @StreamListener(RequeueSink.REQUEUE_INPUT)
    public void receive(String payload) {
        log.info("Received payload : " + payload + ", " + (times++));
        if (times == 3) {
            times = 1;
            throw new AmqpRejectAndDontRequeueException("tried 3 times failed, send to dlq!");
        } else {
            times ++;
            throw new RuntimeException("Message consumer failed!");
        }
    }


}
