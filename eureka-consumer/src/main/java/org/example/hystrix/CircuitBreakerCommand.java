package org.example.hystrix;


import com.netflix.hystrix.*;

public class CircuitBreakerCommand extends HystrixCommand<String> {

    private final static Setter setter =
            Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("CircuitBreaker"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("CircuitBreakerCommand"))
                    .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("CircuitBreakerCommand"))
                    .andCommandPropertiesDefaults(
                            HystrixCommandProperties.Setter()
                            .withCircuitBreakerEnabled(true)
                            .withCircuitBreakerForceOpen(false)
                            .withCircuitBreakerForceClosed(false)
                            .withCircuitBreakerErrorThresholdPercentage(10)//(1) 错误百分比超过10%
                            .withCircuitBreakerRequestVolumeThreshold(5)//(2) 10s以内调用次数5次，同时满足(1)(2)熔断器打开
                            .withCircuitBreakerSleepWindowInMilliseconds(4000)//隔4s之后，熔断器会尝试半开(关闭)，重新放进来请求
            );

    private int index;

    public CircuitBreakerCommand(int index){
        super(setter);
        this.index = index;
    }


    @Override
    protected String run() throws Exception {
        if(index < 5) {	// 模拟成功
            return String.valueOf(index);
        } else if(index<10){
           throw new Exception();
        }else{
            return String.valueOf(index);
        }


    }

    @Override
    protected String getFallback() {
        return "index=["+this.index+"] getFallback";
    }
}
