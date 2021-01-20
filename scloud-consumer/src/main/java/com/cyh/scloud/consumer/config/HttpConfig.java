package com.cyh.scloud.consumer.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Author: huayingcao2
 * Date:  2021/1/20
 * Todo:
 * Time 16:58
 */
@Configuration
public class HttpConfig {

    /**
     * 支持负载均衡
     * @return
     */
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
