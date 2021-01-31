package com.cyh.scloud.consumer.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: cyhua
 * @createTime: 2021/1/31
 * @description: 通过创建HystrixCommand的方式实现断路器
 */
public class UserCommand extends HystrixCommand<List> {

    private RestTemplate restTemplate;

    /**
     * 通过代码的方式设置命令名称、命令组名称和线程池
     *
     * @param restTemplate
     */
    public UserCommand(RestTemplate restTemplate) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("UserCommand"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("getUserListCommand"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("getUserListThread")));
        this.restTemplate = restTemplate;
    }

    /**
     * run方法中实现具体的业务
     *
     * @return
     * @throws Exception
     */
    @Override
    protected List run() throws Exception {
        return restTemplate.getForEntity("http://PROVIDER-BASE/user/getUserList", List.class).getBody();
    }

    /**
     * 重写getFallback方法实现服务降低
     *
     * @return
     */
    @Override
    protected List getFallback() {
        return new ArrayList();
    }


    /**
     * 重写getCacheKey方法，来开启缓存请求
     *
     * @return
     */
    @Override
    protected String getCacheKey() {
        return super.getCacheKey();
    }
}
