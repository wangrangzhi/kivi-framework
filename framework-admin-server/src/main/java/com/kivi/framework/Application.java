package com.kivi.framework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import de.codecentric.boot.admin.config.EnableAdminServer;

/**
 */
@EnableAdminServer
// 自动发现ZK指定节点下的应用进行监控
@EnableDiscoveryClient
@SpringBootApplication
@EnableAutoConfiguration( exclude = { DataSourceAutoConfiguration.class } )
public class Application extends SpringApplication {
    public static void main( String[] args ) {
        SpringApplication.run(Application.class, args);
    }
}
