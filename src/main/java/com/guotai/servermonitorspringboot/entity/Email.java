/*
@File  : Email.java
@Author: WZC
@Date  : 2021-08-24 14:58
*/
package com.guotai.servermonitorspringboot.entity;


import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Email {
    // 主题
    private String subject = "监控资源预警";

    // 主题内容
    private String content = "请注意，监控资源出现预警，及时查看";

    // 接收人邮箱列表
    private String[] receiver = new String[]{"miljia@163.com"};
}
