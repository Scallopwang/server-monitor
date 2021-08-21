/*
@File  : IPMap.java
@Author: WZC
@Date  : 2021-08-20 16:55
*/
package com.guotai.servermonitorspringboot.utils;

import com.guotai.servermonitorspringboot.service.AgentGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class IPMap {
    @Autowired
    AgentGroupService agentGroupService;

    public Map<String, String> getFormMap() {
        return new HashMap<String, String>() {{
            put("machineIP1", agentGroupService.getAllIP().get(0));
            put("machineIP2", agentGroupService.getAllIP().get(1));
        }};
    }
}
