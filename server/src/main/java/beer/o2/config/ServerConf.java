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
    @Value("${server.name}")
    private String servername;
    @Value("${server.port}")
    private String port;
    @Value("${server.ip}")
    private String ip;
    @Value("${spring.profiles.active}")
    private String active;
    @Value("${spring.datasource.url}")
    private String dataUrl;

}
