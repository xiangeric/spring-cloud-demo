package org.example.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import org.springframework.web.client.RestTemplate;

public class CacheHystrixCommand<T> extends HystrixCommand<T> {

    private String cacheKey;

    private RestTemplate restTemplate;

    private String url;

    private Class<T> targetClass;

    public CacheHystrixCommand(Setter setter,String cacheKey,
                                  RestTemplate restTemplate,String url, Class<T> targetClass){
        super(setter);
        this.cacheKey = cacheKey;
        this.restTemplate = restTemplate;
        this.url = url;
        this.targetClass = targetClass;
    }

    @Override
    protected T run() throws Exception {
        System.out.println("run....");
        T result = restTemplate.getForEntity(this.url,this.targetClass).getBody();
        return result;
    }

    @Override
    protected String getCacheKey() {
        return this.cacheKey;
    }

    public static void clearHystrixCommandCache(String commandKey,String cacheKey){
        HystrixRequestCache.getInstance(
                HystrixCommandKey.Factory.asKey(commandKey),
                HystrixConcurrencyStrategyDefault.getInstance())
                .clear(cacheKey);
    }
}
