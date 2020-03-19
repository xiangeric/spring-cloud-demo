package org.example.stream.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 死信队列Sink
 **/
public interface DlqSink {

    public static String INPUT = "dlqInput";

    @Input(INPUT)
    SubscribableChannel input();
}
