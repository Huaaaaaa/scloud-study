package com.cyh.scloud.base.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author: cyhua
 * @createTime: 2021/1/20
 * @description:
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @GetMapping("/indexUser")
    public String indexUser(){
        return "User Controller";
    }

    @GetMapping("/getUserList")
    public List<Map<String, Object>> index(){
        ArrayList<Map<String, Object>> users = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            HashMap<String,Object> user = new HashMap<>();
            user.put("name","user"+i);
            user.put("age",i*10);
            user.put("address","address"+ UUID.randomUUID().toString());
            users.add(user);
        }
        return users;
        
    }
}
