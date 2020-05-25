package beer.o2.modules.sys.controller;

import beer.o2.modules.sys.domain.user.entity.SysUserDO;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author:bomber
 * @Date:Created in 8:05 下午 2020/5/25
 * @Description:
 * @Modified By:
 */
public abstract class AbstractController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected SysUserDO getUser() {
        return (SysUserDO) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getUserId() {
        return getUser().getUserId();
    }
}
