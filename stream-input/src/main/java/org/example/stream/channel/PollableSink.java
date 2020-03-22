package org.example.stream.channel;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.binder.PollableMessageSource;

public interface PollableSink {

    public static final String POLLABLE_INPUT = "pollableInput";

    @Input(POLLABLE_INPUT)
    PollableMessageSource input();
}
