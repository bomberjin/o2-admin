package com.dbshs.show.config;

import com.dbshs.show.ApplicationStartup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:bomber
 * @Date:Created in 下午1:39 2018/5/17
 * @Description: 启动配置
 * @Modified By:
 */
@Configuration
public class ListenerConfig {

    /**
     * 启动配置输出
     * @return
     */
    @Bean
    public ApplicationStartup bmAdminApplicationStartup(){
        return new ApplicationStartup();
    }
}
