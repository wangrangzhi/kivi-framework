package com.kivi.framwork.actuator.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kivi.framework.component.ApplicationKit;
import com.kivi.framework.util.kit.StrKit;

//@Controller
//@RequestMapping("/actuator")
public class ActuatorController {

    // @Value( "${management.context-path}" )
    private String contextPath;

    @RequestMapping( "" )
    public String actuator( ModelMap map ) {
        String manageContextPath = "";
        if (StrKit.isBlank(contextPath) || "/".equals(contextPath))
            manageContextPath = "";

        if (!StrKit.endWith(contextPath, "/", false))
            manageContextPath = contextPath + "/";

        String pathBase = ApplicationKit.me().getServerContext();

        manageContextPath = pathBase + manageContextPath;

        Map<String, String> actuatorMap = new HashMap<>();
        actuatorMap.put(manageContextPath + "autoconfig",
                "显示一个auto-configuration的报告，该报告展示所有auto-configuration候选者及它们被应用或未被应用的原因");
        actuatorMap.put(manageContextPath + "beans", "显示一个应用中所有Spring Beans的完整列表");
        actuatorMap.put(manageContextPath + "configprops", "显示一个所有@ConfigurationProperties的整理列表");
        actuatorMap.put(manageContextPath + "dump", "执行一个线程转储");
        actuatorMap.put(manageContextPath + "env", "暴露来自Spring　ConfigurableEnvironment的属性");
        actuatorMap.put(manageContextPath + "health", "展示应用的健康信息（当使用一个未认证连接访问时显示一个简单的’status’，使用认证连接访问则显示全部信息详情）");
        actuatorMap.put(manageContextPath + "info", "显示任意的应用信息");
        actuatorMap.put(manageContextPath + "metrics", "展示当前应用的’指标’信息");
        actuatorMap.put(manageContextPath + "mappings", "显示一个所有@RequestMapping路径的整理列表");
        actuatorMap.put(manageContextPath + "shutdown", "允许应用以优雅的方式关闭（默认情况下不启用）");
        actuatorMap.put(manageContextPath + "trace", "显示trace信息（默认为最新的一些HTTP请求）");

        map.addAttribute("actuatorMap", actuatorMap);
        map.addAttribute("applicationName", ApplicationKit.me().getAppcationName());
        return "actuator";
    }

}
