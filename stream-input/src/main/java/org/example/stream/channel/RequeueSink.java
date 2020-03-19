package org.example.stream.channel;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface RequeueSink {

    public static String REQUEUE_INPUT = "requeueInput";

    @Input(REQUEUE_INPUT)
    SubscribableChannel input();
}
