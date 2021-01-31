package com.cyh.scloud.consumer.command;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixObservableCommand;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

import java.util.List;

/**
 * @author: cyhua
 * @createTime: 2021/1/31
 * @description:
 */
public class UserObservableCommand extends HystrixObservableCommand<List> {

    private RestTemplate restTemplate;

    /**
     * 通过代码的方式设置命令名称、命令组名称和线程池
     *
     * @param restTemplate
     */
    public UserObservableCommand(RestTemplate restTemplate) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("UserCommand"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("getUserListCommand")));
        this.restTemplate = restTemplate;
    }

    /**
     * 使用HystrixObservableCommand来实现命令封装，需要将命令的执行逻辑在construct方法中重载
     *
     * @return
     */
    @Override
    protected Observable<List> construct() {
        return Observable.create(subscriber -> {
            if (!subscriber.isUnsubscribed()) {
                List res = restTemplate.getForEntity("http://PROVIDER-BASE/user/getUserList", List.class).getBody();
                subscriber.onNext(res);
                subscriber.onCompleted();
            }
        });
    }

    /**
     * 重写getCacheKey方法来开启缓存请求
     *
     * @return
     */
    @Override
    protected String getCacheKey() {
        return super.getCacheKey();
    }
}
