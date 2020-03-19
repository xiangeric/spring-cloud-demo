package org.example.stream.channel;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface AppErrHandlerSink {

    public static String INPUT = "appErrHandlerInput";

    @Input(INPUT)
    SubscribableChannel input();
}
