package org.example.stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(StreamSink.class)
@Slf4j
public class StreamReceiver {

    @Value("${server.port}")
    private Integer serverPort;

    @StreamListener(StreamSink.INPUT)
    public void receive(String message){
        message = "receiver ["+serverPort+"] receive:"+message;
        log.info(message);
    }

}
