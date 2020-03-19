package org.example.stream.component;

import lombok.extern.slf4j.Slf4j;
import org.example.constants.FileConstants;
import org.example.stream.channel.FileProcessor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.example.constants.FileConstants.FILE_NAME_HEADER;

@Slf4j
@EnableBinding(FileProcessor.class)
public class FileListener {

    private final static String FILE_PATH = "D:\\tmp\\from\\";

    //监听文件获取请求，并将对应的文件发送到对应的destination中
    @StreamListener(FileConstants.GET)
    @SendTo(FileConstants.BACK)
    public Message<byte[]> handle(String fileName) throws IOException {
        log.info("FileListener:::handle "+fileName);
        File f = new File(FILE_PATH + fileName);
        byte[] data = Files.readAllBytes(f.toPath());
        MimeType mimeType = (f.getName().endsWith("gif")) ? MimeTypeUtils.IMAGE_GIF : MimeTypeUtils.IMAGE_JPEG;
        Message<byte[]> message =
                MessageBuilder.withPayload(data)
                        .setHeader(MessageHeaders.CONTENT_TYPE, mimeType)
                        .setHeader(FILE_NAME_HEADER,fileName)
                        .build();
        return message;
    }




}
