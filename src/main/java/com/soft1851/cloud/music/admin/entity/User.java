package com.soft1851.cloud.music.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/4/22
 * @Version 1.0
 */
@Data
@AllArgsConstructor
public class User {
    private String name;
    private String age;
    private Date birthday;
}
