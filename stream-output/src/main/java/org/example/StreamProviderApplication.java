package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.stream.channel.PollableSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
@Slf4j
public class StreamProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamProviderApplication.class,args);
    }

}
