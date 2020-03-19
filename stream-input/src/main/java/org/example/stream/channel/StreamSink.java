package org.example.stream.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface StreamSink {

    public static String INPUT = "streamInput";

    @Input(INPUT)
    SubscribableChannel input();

}
