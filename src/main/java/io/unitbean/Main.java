package io.unitbean;

import io.unitbean.service.props.MinioProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@SpringBootApplication
@EnableConfigurationProperties(MinioProperties.class)
@EnableScheduling
public class Main {
    public static void main(String[] args) {
       SpringApplication.run(Main.class, args);
    }
}