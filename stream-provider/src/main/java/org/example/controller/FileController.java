package org.example.controller;

import org.example.entity.Resp;
import org.example.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/{fileName}")
    public Resp get(@PathVariable String fileName){
        Resp result;
        if(!StringUtils.isEmpty(fileName)){
            boolean isSuccess = fileService.req(fileName);
            if(isSuccess){
                result = Resp.ofSuccess(null);
            }else{
                result = Resp.ofFail("文件请求失败");
            }
        }else{
            result = Resp.ofFail("fileName 不能为空");
        }
        return result;
    }
}
