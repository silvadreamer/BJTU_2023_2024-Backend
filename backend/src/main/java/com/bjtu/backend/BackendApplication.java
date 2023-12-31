package com.bjtu.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class BackendApplication
{

    public static void main(String[] args)
    {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        SpringApplication.run(BackendApplication.class, args);
    }

}
