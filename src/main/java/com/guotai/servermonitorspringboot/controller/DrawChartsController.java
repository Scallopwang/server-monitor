/*
@File  : DrawChartsController.java
@Author: WZC
@Date  : 2021-08-23 10:54
*/
package com.guotai.servermonitorspringboot.controller;

import com.guotai.servermonitorspringboot.service.LocalAgentService;
import com.guotai.servermonitorspringboot.utils.IPMap;
import com.guotai.servermonitorspringboot.utils.TimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DrawChartsController {
    @Autowired
    private LocalAgentService localAgentService;
    @Autowired
    private TimeFormat timeFormat;
    @Autowired
    private IPMap ipMap;

    private Map<String, String> timeChoiceMap = new HashMap<String, String>() {{
        put("day", "%Y-%m-%d 10:00:00");
        put("hour", "%Y-%m-%d %H:00:00");
        put("minute", "%Y-%m-%d %H:%i:00");
    }};

    @RequestMapping("/drawCharts")
    @ResponseBody
    public Map<String, Object> drawCharts(HttpServletRequest request) {
        Map<String, Object> drawMap = new HashMap<>();
        // 获取ip、时间起止点画图
        String cpuChooseIp = request.getParameter("cpuChooseIp");
        String cpu_date1 = request.getParameter("cpu_date1");
        String cpu_date2 = request.getParameter("cpu_date2");
        String timeChoice = request.getParameter("timeChoice");

        if (cpuChooseIp != null && !cpuChooseIp.equals("") && cpu_date1 != null && !cpu_date1.equals("") && cpu_date2 != null && !cpu_date2.equals("")) {
            Timestamp timestamp1 = timeFormat.timeFormat(cpu_date1);
            Timestamp timestamp2 = timeFormat.timeFormat(cpu_date2);
            List<Timestamp> timeSection = localAgentService.getTimeSection(cpuChooseIp, timestamp1, timestamp2, timeChoiceMap.get(timeChoice));
            List<Double> cpuFreeSection = localAgentService.getCpuFreeSection(cpuChooseIp, timestamp1, timestamp2, timeChoiceMap.get(timeChoice));
            System.out.println(timeSection);
            System.out.println(cpuFreeSection);
            drawMap.put("timeSection", timeSection);
            drawMap.put("cpuFreeSection", cpuFreeSection);
            return drawMap;
        } else {
            // 默认使用机器1的IP
            List<Timestamp> timeSection = localAgentService.getLastTimeSection(ipMap.getFormMap().get("machineIP1"));
            List<Double> cpuFreeSection = localAgentService.getLastCpuFreeSection(ipMap.getFormMap().get("machineIP1"));
            drawMap.put("timeSection", timeSection);
            drawMap.put("cpuFreeSection", cpuFreeSection);
            return drawMap;
        }
    }

    @RequestMapping("/memDrawCharts")
    @ResponseBody
    public Map<String, Object> memDrawCharts(HttpServletRequest request) {
        Map<String, Object> drawMap = new HashMap<>();
        // 获取ip、时间起止点画图
        String memChooseIp = request.getParameter("memChooseIp");
        String mem_date1 = request.getParameter("mem_date1");
        String mem_date2 = request.getParameter("mem_date2");
        String memTimeChoice = request.getParameter("memTimeChoice");

        if (memChooseIp != null && !memChooseIp.equals("") && mem_date1 != null && !mem_date1.equals("") && mem_date2 != null && !mem_date2.equals("")) {
            Timestamp timestamp1 = timeFormat.timeFormat(mem_date1);
            Timestamp timestamp2 = timeFormat.timeFormat(mem_date2);
            List<Timestamp> timeSection = localAgentService.getTimeSection(memChooseIp, timestamp1, timestamp2, timeChoiceMap.get(memTimeChoice));
            List<Double> memFreeSection = localAgentService.getMemFreeSection(memChooseIp, timestamp1, timestamp2, timeChoiceMap.get(memTimeChoice));
            drawMap.put("timeSection", timeSection);
            drawMap.put("memFreeSection", memFreeSection);
            return drawMap;
        } else {
            // 默认使用机器1的IP
            List<Timestamp> timeSection = localAgentService.getLastTimeSection(ipMap.getFormMap().get("machineIP1"));
            List<Double> memFreeSection = localAgentService.getLastMemFreeSection(ipMap.getFormMap().get("machineIP1"));
            drawMap.put("timeSection", timeSection);
            drawMap.put("memFreeSection", memFreeSection);
            return drawMap;
        }
    }
}
