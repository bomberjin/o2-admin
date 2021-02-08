package beer.o2.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:bomber
 * @Date:Created in 下午1:39 2018/5/17
 * @Description: 服务器配置信息
 * @Modified By:
 */
@Configuration
@Getter
public class ServerConf {
    /**
     * 服务名称
     */
    @Value("${server.name}")
    private String servername;

    /**
     * 端口号
     */
    @Value("${server.port}")
    private String port;

    /**
     * ip地址
     */
    @Value("${server.ip}")
    private String ip;

    /**
     * 激活文件
     */
    @Value("${spring.profiles.active}")
    private String active;

    /**
     * 数据库地址
     */
    @Value("${spring.datasource.url}")
    private String dataUrl;

}
