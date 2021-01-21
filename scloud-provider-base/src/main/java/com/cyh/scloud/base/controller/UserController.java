package com.cyh.scloud.base.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @GetMapping("/getUserList")
    public List<Map<String, Object>> index() {
        log.info("UserController index begin to get userList");
        ArrayList<Map<String, Object>> users = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            HashMap<String, Object> user = new HashMap<>();
            user.put("name", "user" + i);
            user.put("age", i * 10);
            user.put("address", "address" + UUID.randomUUID().toString());
            users.add(user);
        }
        return users;
    }

}
