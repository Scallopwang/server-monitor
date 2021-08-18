/*
@File  : ThriftCommunication.java
@Author: WZC
@Date  : 2021-08-18 14:59
*/
package com.guotai.servermonitorspringboot.entity;

import org.springframework.stereotype.Component;

@Component
public class ThriftCommunication {
    public String commandRes;

    public String getCommandRes() {
        return commandRes;
    }

    public void setCommandRes(String commandRes) {
        this.commandRes = commandRes;
    }
}
