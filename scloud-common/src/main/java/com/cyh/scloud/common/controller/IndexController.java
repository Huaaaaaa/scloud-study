package com.cyh.scloud.common.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: huayingcao2
 * Date:  2021/1/20
 * Todo:
 * Time 12:33
 */
@RestController
public class IndexController {


    @GetMapping("/index")
    public String index() {
        return "Scloud-common is alive";
    }
}
