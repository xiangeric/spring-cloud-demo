package org.example.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface StreamSource {

    public static String OUTPUT = "streamOutput";

    @Output(OUTPUT)
    public MessageChannel output();
}
