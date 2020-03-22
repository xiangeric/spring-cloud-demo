package org.example.stream.channel;


import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface PollableSource {

    public static String POLL_OUTPUT = "pollableOutput";

    @Output(POLL_OUTPUT)
    MessageChannel output();

}
