package beer.o2.modules.sys.service.impl;

import beer.o2.modules.sys.dao.*;
import beer.o2.modules.sys.domain.pub.entity.SysMenuDO;
import beer.o2.modules.sys.service.SysMenuService;
import beer.o2.modules.sys.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:bomber
 * @Date:Created in 8:32 下午 2020/5/25
 * @Description:
 * @Modified By:
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    SysMenuRepository sysMenuRepository;

    @Autowired
    SysRoleMenuRepository sysRoleMenuRepository;

    @Autowired
    SysUserService sysUserService;



    @Override
    public List<SysMenuDO> queryListParentId(Long parentId, List<Long> menuIdList) {
        List<SysMenuDO> menuList = queryListParentId(parentId);
        if(menuIdList == null){
            return menuList;
        }

        List<SysMenuDO> userMenuList = new ArrayList<>();
        for(SysMenuDO menu : menuList){
            if(menuIdList.contains(menu.getMenuId())){
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    @Override
    public List<SysMenuDO> queryListParentId(Long parentId) {
        return sysMenuRepository.findAllByParentIdOrderByOrderNum(parentId);
    }

    @Override
    public List<SysMenuDO> queryNotButtonList() {
        return sysMenuRepository.queryNotButtonList();
    }

    @Override
    public List<SysMenuDO> getUserMenuList(Long userId) {
        //系统管理员，拥有最高权限
        if(userId == 1){
            return getAllMenuList(null);
        }

        //用户菜单列表
        List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    @Override
    public void delete(Long menuId){
        //删除菜单
        sysMenuRepository.deleteById(menuId);
        //删除菜单与角色关联
        sysRoleMenuRepository.deleteAllByMenuId(menuId);
    }

    @Override
    public List<SysMenuDO> list() {

        return sysMenuRepository.findAll();
    }

    @Override
    public SysMenuDO getById(Long id) {
        return sysMenuRepository.findById(id).get();
    }

    @Override
    public void save(SysMenuDO menu) {
        sysMenuRepository.save(menu);
    }

    @Override
    public void updateById(SysMenuDO menu) {
        sysMenuRepository.save(menu);
    }

    /**
     * 获取所有菜单列表
     */
    private List<SysMenuDO> getAllMenuList(List<Long> menuIdList){
        //查询根菜单列表
        List<SysMenuDO> menuList = queryListParentId(0L, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }

    /**
     * 递归
     */
    private List<SysMenuDO> getMenuTreeList(List<SysMenuDO> menuList, List<Long> menuIdList){
        List<SysMenuDO> subMenuList = new ArrayList<SysMenuDO>();

        for(SysMenuDO entity : menuList){
            //目录
            if(entity.getType() == 0){
                entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        }

        return subMenuList;
    }
}
