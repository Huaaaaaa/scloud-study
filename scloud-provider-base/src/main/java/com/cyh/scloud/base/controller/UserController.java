package com.cyh.scloud.base.controller;

import com.cyh.scloud.commonapi.base.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: cyhua
 * @createTime: 2021/1/20
 * @description:
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {


    @GetMapping("/indexUser")
    public String indexUser() {
        return "User Controller";
    }

    private ConcurrentHashMap userCache = new ConcurrentHashMap<String, User>();

    /**
     * 获取列表
     *
     * @return
     */
    @GetMapping("/getUserList")
    @ResponseBody
    public List<User> getUserList() {
        log.info("UserController index begin to get userList");
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            User user = new User();
            String userId = "loginName" + i;
            user.setLoginName(userId);
            user.setNickName("nickName" + i);
            user.setFirstName("firstName" + i);
            user.setLastName("lastName" + i);
            user.setAge(i * 10 + 1);
            user.setSex(i % 2 == 0 ? "male" : "female");
            users.add(user);
            userCache.put(userId, user);
        }
        return users;
    }

    /**
     * 根据userId获取用户
     *
     * @param userId
     * @return
     */
    @GetMapping("/getUserById/{userId}")
    @ResponseBody
    public User getUserById(@PathVariable String userId) {
        log.info("UserController getUserById begin:userId={}", userId);
        if (!CollectionUtils.isEmpty(userCache) && userCache.contains(userId)) {
            return (User) userCache.get(userId);
        }
        return null;
    }

    /**
     * 根据userId获取用户
     *
     * @param userIds
     * @return
     */
    @GetMapping("/getUserByIds/{userIds}")
    @ResponseBody
    public List<User> getUserByIds(@PathVariable String userIds) {
        log.info("UserController getUserByIds begin:userIds={}", userIds);
        List<User> users = new ArrayList<>();
        if (!CollectionUtils.isEmpty(userCache) && !StringUtils.isEmpty(userIds)) {
            List<String> ids = Arrays.asList(userIds);
            for (String userId : ids) {
                if (userCache.contains(userId)) {
                    User user = (User) userCache.get(userId);
                    users.add(user);
                }
            }
        }
        return users;
    }

    /**
     * 根据userId修改用户信息
     *
     * @param user
     * @return
     */
    @PostMapping(value = "/updateUserByUserId")
    @ResponseBody
    public User updateUserByUserId(@RequestBody User user) {
        log.info("UserController updateUserByUserId begin:user={}", user);
        User newUser = user;
        if (!CollectionUtils.isEmpty(userCache) && !StringUtils.isEmpty(user.getLoginName()) && userCache.contains(user.getLoginName())) {
            userCache.put(user.getLoginName(), user);
            newUser = (User) userCache.get(user.getLoginName());
        }
        return newUser;
    }

}
