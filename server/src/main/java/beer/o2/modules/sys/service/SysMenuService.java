package beer.o2.modules.sys.service;

import beer.o2.modules.sys.domain.pub.entity.SysMenuDO;

import java.util.List;

/**
 * @Author:bomber
 * @Date:Created in 8:31 下午 2020/5/25
 * @Description:
 * @Modified By:
 */
public interface SysMenuService {

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     * @param menuIdList  用户菜单ID
     */
    List<SysMenuDO> queryListParentId(Long parentId, List<Long> menuIdList);

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<SysMenuDO> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenuDO> queryNotButtonList();

    /**
     * 获取用户菜单列表
     */
    List<SysMenuDO> getUserMenuList(Long userId);

    /**
     * 删除
     */
    void delete(Long menuId);
}
