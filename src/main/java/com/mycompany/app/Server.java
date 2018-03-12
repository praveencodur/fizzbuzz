package com.mycompany.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Server {

    public static void main (String [] args){
        final Map<String, Object> properties = new HashMap<>();
        properties.put("server.port", "8081");
        properties.put("management.port", "8082");
        properties.put("spring.jmx.default-domain", "h2Server");
        properties.put("endpoints.jmx.domain", "h2server");
        SpringApplication application = new SpringApplicationBuilder().sources(Server.class).properties(properties).build();
        application.run();
    }
}
