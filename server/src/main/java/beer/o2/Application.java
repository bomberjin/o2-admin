package com.dbshs.show;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @Author:bomber
 * @Date:Created in 11:02 上午 2020/5/7
 * @Description:
 * @Modified By:
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication springApplication =new SpringApplication(com.dbshs.show.ApplicationStartup.class);
        springApplication.addListeners(new com.dbshs.show.ApplicationStartup());

        SpringApplication.run(Application.class, args);
    }

}
