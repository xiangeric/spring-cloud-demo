package org.example.stream.component;

import lombok.extern.slf4j.Slf4j;
import org.example.stream.channel.AppErrHandlerSink;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

/**
 * 异常处理
 **/
@EnableBinding({AppErrHandlerSink.class})
@Slf4j
public class AppErrHandlerReceiver {

    @StreamListener(AppErrHandlerSink.INPUT)
    public void receive(String message){
        message = "receiver receive:"+message;
        log.info(message);
        throw new RuntimeException(message);
    }


    @ServiceActivator(inputChannel = "APP_ERR_HANDLER_DIST.myGroup.errors")
    public void handle(Message<?> message) {
        System.out.println("Handling ERROR: " + message);
    }

//    //全局异常处理
//    @StreamListener("errorChannel")
//    public void error(Message<?> message) {
//        System.out.println("Global ERROR: " + message);
//    }
}
