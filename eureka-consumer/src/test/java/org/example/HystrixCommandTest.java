package org.example;

import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.example.entity.Resp;
import org.example.entity.Student;
import org.example.hystrix.*;
import org.example.hystrixfeign.StudentHystrixCollapser;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 以一般方式(即继承方式)测试Hystrix的Command
 */
public class HystrixCommandTest {


    /**
     * 测试HystrixCollapser
     */
    @Test
    public void testHystrixCollapser() throws ExecutionException, InterruptedException {
        //初始化上下文，才能使用请求合并功能
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try{
            Future<Student> future1 = new StudentHystrixCollapser(1).queue();
            Future<Student> future2 = new StudentHystrixCollapser(2).queue();
            Future<Student> future3 = new StudentHystrixCollapser(3).queue();
            System.out.println(future1.get());
            System.out.println(future2.get());
            System.out.println(future3.get());
            //判断请求了多少次
            System.out.println("time="+HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().size());
            HystrixCommand<?> command = HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().toArray(new HystrixCommand<?>[1])[0];
            //发出的请求的CommandKey
            System.out.println("commandKey="+command.getCommandKey().name());
            // 请求是否通过“合并”的方式发出
            System.out.println("isCollapsed="+command.getExecutionEvents().contains(HystrixEventType.COLLAPSED));
            // 请求是否成功执行
            System.out.println("isSuccess="+command.getExecutionEvents().contains(HystrixEventType.SUCCESS));

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            context.shutdown();
        }
    }

    /**
     * 测试RequestCache
     */
    @Test
    public void testExtendsRequestCache(){
        final String PROVIDER_URL = "http://localhost:18081/students/1";
        //初始化请求上下文
        HystrixRequestContext.initializeContext();
        String groupName = "groupName";
        String commandKey = "commandKey";
        String cacheKey = "cacheKey";
        HystrixCommand.Setter setter = HystrixCommand.Setter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey(groupName))
                .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey));
        CacheHystrixCommand<Student> command1 = new CacheHystrixCommand(setter,cacheKey,new RestTemplate(),PROVIDER_URL, Student.class);
        CacheHystrixCommand<Student> command2 = new CacheHystrixCommand(setter,cacheKey,new RestTemplate(),PROVIDER_URL, Student.class);
        Student student1 = command1.execute();
        System.out.println(student1.hashCode());
        CacheHystrixCommand.clearHystrixCommandCache(commandKey,cacheKey);
        Student student2 = command2.execute();
        System.out.println(student2.hashCode());
        System.out.println(command2.isResponseFromCache());

    }


    /**
     *
     * 测试HystrixObservable的getFallBack()
     */
    @Test
    public void testHystrixObservableCommandGetFallBack() throws Exception{
        List<Student> list = new ArrayList<>();
        int id;
        for(int i=1;i<5;i++){
            id = i*10;
            list.add(new Student(id,"student"+id,id));
        }
        String groupName = "groupName";
        String commandKey = "commandKey";
        HystrixObservableCommand.Setter setter = HystrixObservableCommand.Setter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey(groupName))
                .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey));
        Observable<Resp> observe = new RestHystrixObservableCommand(setter, list, new RestTemplate()).observe();
        final String[] message = new String[1];
        observe.subscribe(new Observer<Resp>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted...");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                message[0] = e.getMessage();
            }

            @Override
            public void onNext(Resp resp) {
                System.out.println(resp);
            }
        });
        //因为是异步调用，因此阻塞主线程，等待其他线程返回
        Thread.sleep(10000);


    }


    /**
     * 测试HystrixCommand的getFallBack()
     */
    @Test
    public void testHystrixCommandGetFallBack(){
        String groupName = "groupName";
        String commandKey = "commandKey";
        HystrixCommand.Setter setter = HystrixCommand.Setter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey(groupName))
                .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey));
        Student stu = new Student(10,"Tommy",27);
        RestHystrixCommand command = new RestHystrixCommand(setter,new RestTemplate(),stu);
        Resp resp = command.execute();
        System.out.println(resp);
    }


    /**
     * 测试HystrixObservable
     */
    @Test
    public void testStuHystrixObservableCommand() throws InterruptedException {
        List<Integer> list = Arrays.asList(new Integer[]{1,2,3});
        RestTemplate restTemplate = new RestTemplate();
        Observable<Student> observe = new StuHystrixObservableCommand(list, restTemplate).observe();
        observe.subscribe(new Observer<Student>() {
            @Override
            public void onCompleted() {
                System.out.println("work is over");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Student student) {
                System.out.println(student);
            }
        });
        //让主线程睡眠
        Thread.sleep(10000);
    }


    /**
     * 测试HystrixCommand
     */
    @Test
    public void test2() throws Exception{
        MyHystrixCommand command = new MyHystrixCommand("Asynchronous-hystrix");
        //注册观察者事件拦截 
        final Observable<Resp> observe = command.observe();
        //注册结果回调事件,即对返回的结果进行第二次处理
        observe.subscribe(new Action1<Resp>() {
            @Override
            public void call(Resp resp) {
                System.out.println("after return="+resp);
            }
        });

    }



    /**
     * 测试HystrixCommand
     */
    @Test
    public void test() throws Exception{
        //每个Command对象只能调用一次,不可以重复调用,
        //重复调用对应异常信息:This instance can only be executed once. Please instantiate a new instance.
        MyHystrixCommand command = new MyHystrixCommand("Synchronous-hystrix");
        //使用execute()同步调用代码,效果等同于:helloWorldCommand.queue().get();
        Resp result = command.execute();
        System.out.println("result=" + result);

        command = new MyHystrixCommand("Asynchronous-hystrix");
        //异步调用,可自由控制获取结果时机,
        Future<Resp> future = command.queue();
        //get操作不能超过command定义的超时时间,默认:1秒
        result = future.get(100, TimeUnit.MILLISECONDS);
        System.out.println("result=" + result);
        System.out.println("mainThread=" + Thread.currentThread().getName());
    }
}
