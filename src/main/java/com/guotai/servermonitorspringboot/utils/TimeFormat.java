/*
@File  : TimeFormat.java
@Author: WZC
@Date  : 2021-08-17 17:12
*/
package com.guotai.servermonitorspringboot.utils;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class TimeFormat {
    public Timestamp timeFormat(String time) {
        String afterFormatTime = time.replace("T"," ")+":00";
        //格式转换
        return Timestamp.valueOf(afterFormatTime);
    }
}
