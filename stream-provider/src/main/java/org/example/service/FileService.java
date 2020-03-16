package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.constants.FileConstants;
import org.example.entity.Student;
import org.example.stream.FileProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StringUtils;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@EnableBinding(FileProcessor.class)
@Slf4j
public class FileService {

    private final static String COPY_TO_PATH = "D:\\tmp\\to\\";

    @Autowired
    @Qualifier(FileConstants.GET)
    private MessageChannel reqChannel;

    public boolean req(String fileName){
        MimeType mimeType = MimeTypeUtils.TEXT_PLAIN;
        Message<String> message =
                MessageBuilder.withPayload(fileName)
                        .setHeader(MessageHeaders.CONTENT_TYPE, mimeType)
                        .build();
        log.info("FileService:::req "+message);
        return reqChannel.send(message);
    }


    @StreamListener(FileConstants.BACK)
    public void printBackFile(Message<byte[]> message) throws IOException {
       if(message!=null && message.getPayload()!=null && message.getPayload().length>0){
           MessageHeaders headers = message.getHeaders();
           String fileName = headers.get(FileConstants.FILE_NAME_HEADER,String.class);
           if(StringUtils.isEmpty(fileName)){
               fileName = UUID.randomUUID().toString();
           }
           BufferedOutputStream outputStream =
                   new BufferedOutputStream(new FileOutputStream(COPY_TO_PATH + fileName));
           outputStream.write(message.getPayload(),0,message.getPayload().length);
           outputStream.flush();
       }
    }



}
