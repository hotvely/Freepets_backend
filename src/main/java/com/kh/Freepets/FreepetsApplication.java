package com.kh.Freepets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FreepetsApplication
{

    public static void main(String[] args)
    {
        try {
            SpringApplication.run(FreepetsApplication.class, args);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
