package org.example.stream.component;

import lombok.extern.slf4j.Slf4j;
import org.example.stream.channel.StreamSink;
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


    //分区情况异常处理
//    @ServiceActivator(inputChannel = "STREAM_DEMO_DIST.streamGroup-0.errors")
//    public void handle(Message<?> message) {
//        System.out.println("Handling ERROR: " + message);
//    }


}
