package org.example.stream.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface DelaySink {

    public static final String DELAY_INPUT = "delayInput";

    @Input(DELAY_INPUT)
    SubscribableChannel input();
}
