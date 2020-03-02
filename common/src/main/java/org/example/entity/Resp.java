package org.example.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Resp {

    private final static String SUCCESS = "200";
    private final static String FAIL = "500";

    public Resp(String code){
        this.code = code;
    }

    @Getter
    private String code;

    @Getter
    private String message;

    @Getter
    private Object data;

    public static Resp ofSuccess(Object data){
        Resp resp = new Resp(SUCCESS);
        resp.data = data;
        return resp;
    }

    public static Resp ofFail(String message){
        Resp resp = new Resp(FAIL);
        resp.message = message;
        return resp;
    }
}
