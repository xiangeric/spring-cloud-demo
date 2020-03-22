package org.example.stream.component;

import lombok.extern.slf4j.Slf4j;
import org.example.stream.channel.PollableSink;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.binder.RequeueCurrentMessageException;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@EnableBinding(PollableSink.class)
@Slf4j
public class PollableReceiver {

    @Autowired
    private PollableSink pollableSink;

    @Scheduled(fixedDelay = 5_000)
    public void poll() {
        pollableSink.input().poll(message -> {
            byte[] bytes = (byte[]) message.getPayload();
            String payload = new String(bytes);
            log.info("time["+ LocalDateTime.now()+"] receive ["+payload+"]");
//            throw new RequeueCurrentMessageException("fail to consume message!");
        });
    }



}
