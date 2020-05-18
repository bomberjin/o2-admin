package beer.o2.modules.sys.service;

import beer.o2.modules.sys.domain.user.entity.SysUserDO;
import beer.o2.modules.sys.domain.user.entity.SysUserTokenDO;

import java.util.Set;

/**
 * @Author:bomber
 * @Date:Created in 5:16 下午 2020/5/18
 * @Description:
 * @Modified By:
 */
public interface ShiroService {

    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);

    SysUserTokenDO queryByToken(String token);

    /**
     * 根据用户ID，查询用户
     * @param userId
     */
    SysUserDO queryUser(Long userId);
}
