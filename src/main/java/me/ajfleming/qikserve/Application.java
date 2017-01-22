package me.ajfleming.qikserve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *  Class: Application
 *  Purpose: This class holds the method to start up the Spring Application
 *  Author: Andrew Fleming
 */

@SpringBootApplication
public class Application {

    public static void main(String [] args){
        SpringApplication.run(Application.class, args);
    }

}
