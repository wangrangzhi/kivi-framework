package com.kivi.framework.component;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.kivi.framework.exception.ToolBoxException;
import com.kivi.framework.util.kit.StrKit;

/**
 * 应用帮助类，主要用于获取SpringBoot应用的相关信息
 * 
 * @author Eric
 *
 */
@Component
public class ApplicationKit {

    @Autowired
    private ApplicationContext applicationContext;

    public static ApplicationKit me() {

        return SpringContextHolder.getBean(ApplicationKit.class);
    }

    public int getServerPort() {
        ServerProperties serverProperties = SpringContextHolder.getBean(ServerProperties.class);
        if (serverProperties == null)
            return -1;
        return serverProperties.getPort();
    }

    public String getServerContext() {
        ServerProperties serverProperties = SpringContextHolder.getBean(ServerProperties.class);
        if (serverProperties == null)
            return null;
        return serverProperties.getContextPath();
    }

    public String getAppcationName() {
        String name = applicationContext.getApplicationName();
        name = StrKit.removePrefix(name, "/");
        return name;
    }

    public Resource[] getResources( String locationPattern ) {
        Resource[] resources = null;

        try {
            resources = applicationContext.getResources(locationPattern);
        }
        catch (IOException e) {
            throw new ToolBoxException("获取资源文件异常", e);
        }

        return resources;
    }
}
