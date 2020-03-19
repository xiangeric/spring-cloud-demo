package org.example.stream.component;

import lombok.extern.slf4j.Slf4j;
import org.example.stream.channel.RequeueSink;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

import java.util.concurrent.atomic.AtomicInteger;

@EnableBinding(RequeueSink.class)
@Slf4j
public class RequeueReceiver {

    private int times = 0;

    @StreamListener(RequeueSink.REQUEUE_INPUT)
    public void receive(Message<String> message){
        times++;
        throw new RuntimeException("Message consumer failed! [time="+times+"]");
    }

//    @ServiceActivator(inputChannel = "REQUEUE_DIST.requeueGroup.errors")
//    public void handle(Message<?> message) {
//        System.out.println("Handling ERROR: " + message);
//    }

//    //全局异常处理
//    @StreamListener("errorChannel")
//    public void error(Message<?> message) {
//        System.out.println("Global ERROR: " + message);
//    }





}
