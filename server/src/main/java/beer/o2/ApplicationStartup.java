package com.dbshs.show;

import com.dbshs.show.config.ServerConf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @Author:bomber
 * @Date:Created in 11:02 上午 2020/5/7
 * @Description:
 * @Modified By:
 */
@Slf4j
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //服务系统配置
        ServerConf conf = contextRefreshedEvent.getApplicationContext().getBean(ServerConf.class);

        log.info("----------------------  当前服务器配置  ----------------------");

        String activeFile = conf.getActive();
        switch (activeFile) {
            case "dev":
                log.info("配置文件：'开发环境'");
                break;
            case "test":
                log.info("配置文件：'测试环境'");
                break;
            case "pre":
                log.info("配置文件：'预发环境'");
                break;
            case "prod":
                log.info("配置文件：'生产环境'");
                break;
            default:
        }

        log.info("应用名称：" + conf.getServername());
        log.info("应用端口：" + conf.getPort());
        log.info("数据库地址：" + conf.getDataUrl());
        log.info("-----------------------  基本配置完成  -----------------------");
    }
}
