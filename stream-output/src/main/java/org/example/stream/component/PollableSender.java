package org.example.stream.component;

import lombok.extern.slf4j.Slf4j;
import org.example.stream.channel.PollableSource;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EnableBinding(PollableSource.class)
@Slf4j
public class PollableSender {


//    @Bean
//    @InboundChannelAdapter(value = PollableSource.POLL_OUTPUT,
//            poller = @Poller(fixedDelay = "1000", maxMessagesPerPoll = "1"))
    public MessageSource<String> test() {
        return () -> {
            Map<String, Object> map = new HashMap<>(1);
            map.put("type", "dog");
            String str = UUID.randomUUID().toString();
            log.info("test PollableSource:["+str+"]");
            return new GenericMessage<>(str, map);
        };
    }
}
