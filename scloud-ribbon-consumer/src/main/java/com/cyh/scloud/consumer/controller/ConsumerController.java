package com.cyh.scloud.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Author: Huaaaaaa
 * Date:  2021/1/20
 * Todo:
 * Time 17:00
 */
@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    public List add() {
        return restTemplate.getForEntity("http://PROVIDER-BASE/user/getUserList", List.class).getBody();
    }
}
