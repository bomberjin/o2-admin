package beer.o2.modules.sys.domain.user.vo;

import lombok.Data;

/**
 * @Author:bomber
 * @Date:Created in 5:17 下午 2020/5/26
 * @Description: 登录表单
 * @Modified By:
 */
@Data
public class SysLoginFormRequestVO {
    private String username;
    private String password;
    private String captcha;
    private String uuid;
}
