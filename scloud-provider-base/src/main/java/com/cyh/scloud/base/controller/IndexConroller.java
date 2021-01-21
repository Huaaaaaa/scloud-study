package com.cyh.scloud.base.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: cyhua
 * @createTime: 2021/1/20
 * @description:
 */

@RestController
public class IndexConroller {

    @GetMapping("/index")
    public String index() {
        return "Hello Spring Cloud";
    }
}
