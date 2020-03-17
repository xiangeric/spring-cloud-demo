package org.example.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

@EnableBinding(StreamSource.class)
public class StreamSender {

    @Autowired
    @Qualifier(StreamSource.OUTPUT)
    private MessageChannel channel;

    public boolean send(String message){
        MimeType mimeType = MimeTypeUtils.TEXT_PLAIN;
        Message<String> streamMessage = MessageBuilder.withPayload(message)
                .setHeader(MessageHeaders.CONTENT_TYPE,mimeType)
                .build();
        return channel.send(streamMessage);
    }
}
