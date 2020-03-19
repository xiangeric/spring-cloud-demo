package org.example.stream.channel;


import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface RequeueSource {

    public static final String REQUEUE_OUTPUT = "requeueOutput";

    @Output(REQUEUE_OUTPUT)
    public MessageChannel output();
}
