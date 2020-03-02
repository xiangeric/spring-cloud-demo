package org.example.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Resp;

@Slf4j
public class MyHystrixCommand extends HystrixCommand<Resp> {

    private String groupName;

    public MyHystrixCommand(String groupName) {
        //最少配置:指定命令组名(CommandGroup)
        super(HystrixCommandGroupKey.Factory.asKey(groupName));
        this.groupName = groupName;
    }

    @Override
    protected Resp run() throws Exception {
        // 依赖逻辑封装在run()方法中
        log.info("groupName= " + this.groupName +",thread:" + Thread.currentThread().getName());
        return Resp.ofFail("MyHystrixCommand's error");
    }

}
