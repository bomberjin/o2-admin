package beer.o2.modules.sys.service;

import java.util.List;

/**
 * @Author:bomber
 * @Date:Created in 8:59 下午 2020/5/25
 * @Description:
 * @Modified By:
 */
public interface SysRoleMenuService {
    void saveOrUpdate(Long roleId, List<Long> menuIdList);

    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<Long> queryMenuIdList(Long roleId);

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Long[] roleIds);
}
