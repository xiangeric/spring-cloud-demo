package org.example.hystrix;

import com.netflix.hystrix.HystrixObservableCommand;
import org.example.entity.Resp;
import org.example.entity.Student;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class RestHystrixObservableCommand extends HystrixObservableCommand<Resp> {

    private List<Student> students;
    private RestTemplate restTemplate;

    private static String PROVIDER_URL = "http://localhost:18081/students/";

    private int lastIndex;

    public RestHystrixObservableCommand(Setter setter, List<Student> students, RestTemplate restTemplate){
        super(setter);
        this.students = students;
        this.restTemplate = restTemplate;
    }


    @Override
    protected Observable<Resp> construct() {
        return Observable.create(new Observable.OnSubscribe<Resp>() {
            @Override
            public void call(Subscriber<? super Resp> observable) {
                //判断是否已经被订阅
                if(!observable.isUnsubscribed() && students!=null && !students.isEmpty()){
                    try{
                        for(Student student:students){
                            //逐次请求
                            Resp resp = restTemplate.postForEntity(PROVIDER_URL, student,Resp.class).getBody();
                            //进一步处理请求结果
                            observable.onNext(resp);
                        }
                        //完成所有请求之后
                        observable.onCompleted();

                    }catch (Exception e){
                        System.out.println("...error");
                        observable.onError(e);
                    }
                }
            }
        }).doOnNext(new Action1<Resp>() {
            @Override
            public void call(Resp student) {
                lastIndex++;
            }
        }).subscribeOn(Schedulers.computation());
    }

    @Override
    protected Observable<Resp> resumeWithFallback() {
        return Observable.just(Resp.ofFail("there is error when index["+lastIndex+"] request"));
    }
}
