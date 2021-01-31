package com.cyh.scloud.consumer.controller;

import com.cyh.scloud.commonapi.base.domain.User;
import com.cyh.scloud.consumer.command.UserCommand;
import com.cyh.scloud.consumer.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Author: Huaaaaaa
 * Date:  2021/1/20
 * Todo:
 * Time 17:00
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 通过注解方式实现断路器
     *
     * @return
     */
    @RequestMapping(value = "/getUsersByAnnotation", method = RequestMethod.GET)
    public List getUsersByAnnotation() {
        log.info("UserController getUsersByAnnotation begin...");
        return userService.getUsersByAnnotation();
    }

    /**
     * 通过HystrixCommand的方式实现断路器（同步方式）
     *
     * @return
     */
    @RequestMapping(value = "/getUsersBySyncCommand", method = RequestMethod.GET)
    public List getUsersBySyncCommand() {
        log.info("UserController getUsersBySyncCommand begin...");
        return userService.getUsersBySyncCommand();
    }

    /**
     * 通过HystrixCommand的方式实现断路器（异步方式）
     *
     * @return
     */
    @RequestMapping(value = "/getUsersByAsynCommand", method = RequestMethod.GET)
    public List getUsersByAsynCommand() {
        log.info("UserController getUsersByAsynCommand begin...");
        try {
            return userService.getUsersByAsynCommand().get();
        } catch (Exception e) {
            log.error("UserController getUsersByAnnotation error...");
            return null;
        }
    }

    /**
     * 通过HystrixObservableCommand的方式实现断路器
     *
     * @return
     */
    @RequestMapping(value = "/getUsersByObservable", method = RequestMethod.GET)
    public Observable<List> getUsersByObservable() {
        log.info("UserController getUsersByObservable begin...");
        return userService.getUsersByObservable();
    }

    /**
     * getUserById
     *
     * @return
     */
    @RequestMapping(value = "/getUserById/{userId}", method = RequestMethod.GET)
    public User getUserById(@PathVariable String userId) {
        log.info("UserController getUserById begin:userId={}", userId);
        return userService.getUserById(userId);
    }

    /**
     * getUserByIds
     *
     * @return
     */
    @RequestMapping(value = "/getUserByIds/{userIds}", method = RequestMethod.GET)
    public List<User> getUserByIds(@PathVariable String userIds) {
        log.info("UserController getUserByIds begin:userIds={}", userIds);
        return userService.getUserByIds(userIds);
    }


    /**
     * updateUserByUserId
     *
     * @return
     */
    @RequestMapping(value = "/updateUserByUserId", method = RequestMethod.POST)
    public User updateUserByUserId(@RequestBody User user) {
        log.info("UserController updateUserByUserId begin:user={}", user);
        return userService.updateUserByUserId(user);
    }
}
