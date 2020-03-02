package org.example.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import org.example.entity.Student;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import java.util.List;


public class StuHystrixObservableCommand extends HystrixObservableCommand<Student> {

    private List<Integer> ids;

    private RestTemplate restTemplate;

    private Student lastStudent;



    public StuHystrixObservableCommand(List<Integer> ids, RestTemplate restTemplate){
        super(HystrixCommandGroupKey.Factory.asKey("studentCommand"));
        this.ids = ids;
        this.restTemplate = restTemplate;
    }

    @Override
    protected Observable<Student> construct() {
        return Observable.create(new Observable.OnSubscribe<Student>() {
            @Override
            public void call(Subscriber<? super Student> observable) {
                //判断是否已经被订阅
                if(!observable.isUnsubscribed() && ids!=null){
                    final String URL = "http://localhost:18081/students/{1}";
                    try{
                        for(Integer id:ids){
                            //逐次请求
                            Student student = restTemplate.getForEntity(URL,Student.class,id).getBody();
                            //进一步处理请求结果
                            observable.onNext(student);
                        }
                        //完成所有请求之后
                        observable.onCompleted();
                    }catch (Exception e){
                        //发生异常的处理
                        observable.onError(e);
                    }
                }
            }
        });
    }
}
