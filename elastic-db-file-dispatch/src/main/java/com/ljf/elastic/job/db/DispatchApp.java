package com.ljf.elastic.job.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class DispatchApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(DispatchApp.class, args);

    }
}
