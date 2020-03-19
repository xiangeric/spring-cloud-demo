package org.example.stream.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 发送消息，消费的端抛出异常，并将异常信息发送到死信队列
 **/
public interface DlqSource {

    public static String OUTPUT = "dlqOutput";

    @Output(OUTPUT)
    MessageChannel output();

}
