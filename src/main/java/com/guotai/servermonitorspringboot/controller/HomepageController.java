/*
@File  : HomepageController.java
@Author: WZC
@Date  : 2021-08-23 10:56
*/
package com.guotai.servermonitorspringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomepageController {
    @Autowired
    BasicInfoController basicInfoController;
    @Autowired
    ConsoleHeadController consoleHeadController;
    @Autowired
    DrawChartsController drawChartsController;
    @Autowired
    ProcessInfoController processInfoController;

    @RequestMapping("/index")
    public String getIndex(Model model, HttpServletRequest request) {
        basicInfoController.getBasicInfo1();
        basicInfoController.setGroupId(model, request);
        basicInfoController.setAlias(model, request);
        consoleHeadController.exeCommand(model, request);
        consoleHeadController.searchMachine(model, request);
        processInfoController.getProcess1();
        drawChartsController.drawCharts(request);
        return "index";
    }
}
