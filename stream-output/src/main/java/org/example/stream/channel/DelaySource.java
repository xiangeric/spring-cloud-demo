package org.example.stream.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface DelaySource {

    public static String DELAY_OUTPUT = "delayOutput";

    @Output(DELAY_OUTPUT)
    MessageChannel output();
}
