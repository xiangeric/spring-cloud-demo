package org.example;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixEventType;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.example.entity.Student;
import org.example.hystrix.FallBackService;
import org.example.hystrix.HystrixCollapserService;
import org.example.hystrix.HystrixStudentService;
import org.example.hystrix.MyHystrixConfiguration;
import org.example.hystrixfeign.StudentHystrixCollapser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 整合Springboot的测试
 */
@SpringBootTest(classes = MyHystrixConfiguration.class)
@RunWith(SpringRunner.class)
public class HystrixCommandWithSpringBoot {

    @Autowired
    HystrixStudentService studentService;

    @Autowired
    FallBackService fallBackService;

    @Autowired
    private HystrixCollapserService collapserService;



    @Test
    public void testHystrixCollapserService() throws ExecutionException, InterruptedException {
        //初始化上下文，才能使用请求合并功能
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        Future<Student> future1 = collapserService.getStudentById(1);
        Future<Student> future2 = collapserService.getStudentById(2);
        Future<Student> future3 = collapserService.getStudentById(3);
        System.out.println(future1.get());
        System.out.println(future2.get());
        System.out.println(future3.get());
        //判断请求了多少次
        System.out.println("time="+ HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().size());
        HystrixCommand<?> command = HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().toArray(new HystrixCommand<?>[1])[0];
        //发出的请求的CommandKey
        System.out.println("commandKey="+command.getCommandKey().name());
        // 请求是否通过“合并”的方式发出
        System.out.println("isCollapsed="+command.getExecutionEvents().contains(HystrixEventType.COLLAPSED));
        // 请求是否成功执行
        System.out.println("isSuccess="+command.getExecutionEvents().contains(HystrixEventType.SUCCESS));
        context.shutdown();

    }


    /**
     * 测试@HHystrixCommand的fallbackMethod
     */
    @Test
    public void testFallBackMethod(){
        Student student = fallBackService.getStudentById(1);
        System.out.println(student);
    }



    /**
     * 此程序需要关闭其中的provider2和provider3，否则数据返回会出现异常
     * 测试@HHystrixCommand注解，以及@CacheRemove、@CacheKey、@CacheResult
     */
    @Test
    public void test2(){
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        Student student = studentService.getStudentById(1);
        System.out.println(student);
        student.setName("FFFF");
        studentService.updateStudentById(1,student);
        student = studentService.getStudentById(1);
        System.out.println(student);
        context.close();
    }

    /**
     * 测试@HystrixCommand注解
     */
    @Test
    public void test(){
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        Student student = studentService.getStudentById(1);
        System.out.println(student);
        student.setName("FFFF");
        System.out.println(student);
        context.close();
    }
}
