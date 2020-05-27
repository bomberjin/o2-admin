package beer.o2.modules.sys.service;

import beer.o2.common.util.PageUtils;
import beer.o2.modules.sys.domain.role.entity.SysRoleDO;

import java.util.List;
import java.util.Map;

/**
 * @Author:bomber
 * @Date:Created in 9:23 下午 2020/5/25
 * @Description:
 * @Modified By:
 */
public interface SysRoleService {
    PageUtils queryPage(Map<String, Object> params);

    void saveRole(SysRoleDO role);

    void update(SysRoleDO role);

    void deleteBatch(Long[] roleIds);


    /**
     * 查询用户创建的角色ID列表
     */
    List<Long> queryRoleIdList(Long createUserId);

    SysRoleDO getById(Long roleId);

    List<SysRoleDO> listByMap(Long  userId);
}
