package org.example.stream;

import org.example.constants.FileConstants;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface FileProcessor {

    //监听获取文件请求
    @Input(FileConstants.GET)
    SubscribableChannel get();

    //向外部文件信息
    @Output(FileConstants.BACK)
    MessageChannel back();

}
