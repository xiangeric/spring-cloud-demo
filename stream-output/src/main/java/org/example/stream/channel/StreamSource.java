package org.example.stream.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
/**
 * 分组、分区
 **/
public interface StreamSource {

    public static String OUTPUT = "streamOutput";

    @Output(OUTPUT)
    public MessageChannel output();
}
