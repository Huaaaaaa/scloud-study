package com.cyh.scloud.commonapi.base.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: cyhua
 * @createTime: 2021/1/31
 * @description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = -2563784497369690822L;

    private String loginName;

    private String nickName;

    private String firstName;

    private String lastName;

    private Integer age;

    private String sex;
}
