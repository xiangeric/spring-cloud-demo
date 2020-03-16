package org.example.stream;

import org.example.constants.FileConstants;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface FileProcessor {

    //向外部请求获得文件信息
    @Output(FileConstants.GET)
    MessageChannel get();

    //监听文件传回信息
    @Input(FileConstants.BACK)
    SubscribableChannel back();

}
