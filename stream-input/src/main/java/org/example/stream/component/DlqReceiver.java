package org.example.stream.component;

import lombok.extern.slf4j.Slf4j;
import org.example.stream.channel.DlqSink;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

/**
 * 当异常抛出时，将信息发送到死信队列
 **/
@EnableBinding(DlqSink.class)
@Slf4j
public class DlqReceiver {

    @StreamListener(DlqSink.INPUT)
    public void handle(Message<String> message){
        log.info(message.getPayload());
        throw new RuntimeException("error receive:"+message);
    }

    @ServiceActivator(inputChannel = "EXAMPLE_DLQ_DIST.dlqGroup.errors")
    public void handleError(Message<?> message) {
        System.out.println("Handling ERROR: " + message);
        throw new RuntimeException ("Message consumer failed!");
    }
}
