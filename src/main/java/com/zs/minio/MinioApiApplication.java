package com.zs.minio;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author word
 */
@EnableConfigurationProperties
@MapperScan("com.zs.minio.mapper")
@SpringBootApplication
public class MinioApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinioApiApplication.class, args);
    }

}
