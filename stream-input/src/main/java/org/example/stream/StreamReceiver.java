package org.example.stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

@EnableBinding(StreamSink.class)
@Slf4j
public class StreamReceiver {

    @Value("${server.port}")
    private Integer serverPort;

    //正常情况
//    @StreamListener(StreamSink.INPUT)
//    public void receive(String message){
//        message = "receiver ["+serverPort+"] receive:"+message;
//        log.info(message);
//    }

    //错误情况
    @StreamListener(StreamSink.INPUT)
    public void receive(String message){
        message = "receiver ["+serverPort+"] receive:"+message;
        log.info(message);
        throw new RuntimeException(message);
    }


    @ServiceActivator(inputChannel = "STREAM-DEMO-DIST.streamGroup.errors")
    public void handle(Message<?> message) {
        System.out.println("Handling ERROR: " + message);
    }


}
