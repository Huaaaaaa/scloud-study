package com.cyh.scloud.consumer.service;

import com.cyh.scloud.commonapi.base.domain.User;
import com.cyh.scloud.consumer.command.UserCommand;
import com.cyh.scloud.consumer.command.UserObservableCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @author: cyhua
 * @createTime: 2021/1/31
 * @description:
 */
@Service
public class UserService extends CommonService {

    @Autowired
    private RestTemplate restTemplate;

    private String userId;

    private String userIds;

    /**
     * 通过注解的方式实现断路器（熔断/短路）
     * fallbackMethod属性:指向调用接口失败时的方法，实现服务降级
     * ignoreExceptions属性：当HystrixCommand中的run方法中抛出异常时，除了HystrixBadRequestException异常之外，
     * 其他的异常均会被Hystrix认为命令执行失败并触发服务降低的逻辑，所以当需要在命令执行中抛出不触发服务降低的异常时可以使用该属性
     * commandKey：命令名称，可选，默认值为类名
     * groupKey：分组，通过设置命令组，Hystrix会根据组来组织和统计命令告警、仪表盘等信息，除此之外，线程池的划分也是根据命令组来实现，
     * 通常情况下，会让组名称相同的命令使用同一个线程池，所以需要在创建HystrixCommand命令时为其指定命令组来实现默认的线程池划分
     * threadPoolKey：线程池划分，除了根据命令组之外，还能通过threadPoolKey来划分线程池。
     * 通常情况下，如果没有指定threadPoolKey,依然会使用命令组来划分线程池，但是尽量通过threadPoolKey的方式来指定线程池的划分，
     * 因为多个不同的命令可能从业务逻辑上来看属于同一个组，但是从实现上来看需要与其他命令进行隔离，使用同一个线程池会有问题
     *
     * @return
     */
    @HystrixCommand(fallbackMethod = "commonFullbackAndReturnException", ignoreExceptions = {HystrixBadRequestException.class}, commandKey = "getUserList", groupKey = "UserGroup", threadPoolKey = "getUserListThread")
    public List getUsersByAnnotation() {
        return restTemplate.getForEntity("http://PROVIDER-BASE/user/getUserList", List.class).getBody();
    }

    /**
     * 根据id查询用户信息
     *
     * @param userId
     * @return
     * @CacheResult:使用@CacheResult注解开启缓存，会将请求结果置入到缓存中，默认的缓存key是所有参数，如该方法中的userId 可以通过cacheKeyMethod属性来指定具体缓存key的生成规则，也可以用过@CacheKey注解在方法参数中指定用户组装缓存key的元素，如下
     * 但是cacheKeyMethod的优先级高于注解@CacheKey
     * @HystrixCollapser:请求合并器注解，当多个id的请求在指定时间窗口内频繁执行时，可以将单个请求合并成批量请求，以减少网络开销 这个需要保证服务端提供单个和批量查询的相关接口
     */
    @CacheResult
    @HystrixCollapser(batchMethod = "getUserByIds", collapserProperties = {@HystrixProperty(name = "timerDelayInMilliseconds", value = "100")})
    @HystrixCommand(fallbackMethod = "commonFullbackAndReturnException", ignoreExceptions = {HystrixBadRequestException.class}, commandKey = "getUserById", groupKey = "UserGroup", threadPoolKey = "getUserByIdThread")
    public User getUserById(@CacheKey("userId") String userId) {
        return restTemplate.getForEntity("http://PROVIDER-BASE/user/getUserById/" + userId, User.class).getBody();
    }

    /**
     * 根据多个id查询多个用户的信息
     * 通过cacheKeyMethod属性来指定具体缓存key的生成规则
     *
     * @param userIds
     * @return
     */
    @CacheResult(cacheKeyMethod = "getUserIdsCacheKey")
    @HystrixCommand(fallbackMethod = "commonFullbackAndReturnException", ignoreExceptions = {HystrixBadRequestException.class}, commandKey = "getUserByIds", groupKey = "UserGroup", threadPoolKey = "getUserByIdsThread")
    public List getUserByIds(String userIds) {
        this.userIds = userIds;
        return restTemplate.getForEntity("http://PROVIDER-BASE/user/getUserByIds/" + userIds, List.class).getBody();
    }

    /**
     * 根据id更新某个用户的信息
     * 当更新已经缓存过的数据时，缓存会失效，这种情况下要进行缓存清理操作
     *
     * @param user
     * @return
     */
    @CacheRemove(commandKey = "getRemoveCacheKey")
    @HystrixCommand(fallbackMethod = "commonFullbackAndReturnException", ignoreExceptions = {HystrixBadRequestException.class}, commandKey = "updateUserByUserId", groupKey = "UserGroup", threadPoolKey = "updateUserByUserIdThread")
    public User updateUserByUserId(User user) {
        return restTemplate.postForEntity("http://PROVIDER-BASE/user/updateUserByUserId", user, User.class).getBody();
    }


    /**
     * 通过HystrixCommand的方式实现断路器（同步方式）
     *
     * @return
     */
    public List getUsersBySyncCommand() {
        UserCommand uc = new UserCommand(restTemplate);
        return uc.execute();
    }

    /**
     * 通过HystrixCommand的方式实现断路器（异步方式）
     *
     * @return
     */
    public Future<List> getUsersByAsynCommand() {
        UserCommand uc = new UserCommand(restTemplate);
        return uc.queue();
    }


    /**
     * 通过HystrixObservableCommand的方式实现断路器
     *
     * @return
     */
    public Observable<List> getUsersByObservable() {
        UserObservableCommand uoc = new UserObservableCommand(restTemplate);
        //observe返回一个Hot Observable,调用之后，命令会立即被执行，每次调用都会重放行为
        return uoc.observe();
        //toObservable返回一个Cold Observable,执调用后，命令不会立即被执行，只有当所有订阅者都订阅之后才会执行
//        return uoc.toObservable();
    }


    /**
     * 定义批量查詢多個用戶請求的緩存key
     *
     * @return
     */
    private String getUserIdsCacheKey() {
        return userIds;
    }

    /**
     * 获取要清除的缓存key
     *
     * @param user
     * @return
     */
    private String getRemoveCacheKey(User user) {
        return user.getFirstName();
    }


}
