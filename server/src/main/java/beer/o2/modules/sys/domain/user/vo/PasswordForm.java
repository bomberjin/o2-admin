package beer.o2.modules.sys.domain.user.vo;

import lombok.Data;

/**
 * @Author:bomber
 * @Date:Created in 9:46 下午 2020/5/26
 * @Description:
 * @Modified By:
 */
@Data
public class PasswordForm {
    /**
     * 原密码
     */
    private String password;
    /**
     * 新密码
     */
    private String newPassword;
}
