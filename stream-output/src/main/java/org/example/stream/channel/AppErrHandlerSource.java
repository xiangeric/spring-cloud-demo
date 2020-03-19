package org.example.stream.channel;


import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * application - level异常处理
 **/
public interface AppErrHandlerSource {

    public static String OUTPUT = "appErrHandlerOutput";

    @Output(OUTPUT)
    public MessageChannel output();

}
