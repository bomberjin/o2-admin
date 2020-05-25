package beer.o2.modules.sys.service.impl;

import beer.o2.modules.sys.dao.SysRoleMenuRepository;
import beer.o2.modules.sys.domain.pub.entity.SysRoleMenuDO;
import beer.o2.modules.sys.service.SysRoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:bomber
 * @Date:Created in 9:00 下午 2020/5/25
 * @Description:
 * @Modified By:
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
    @Autowired
    SysRoleMenuRepository sysRoleMenuRepository;

    @Override
    public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
        //先删除角色与菜单关系
        deleteBatch(new Long[]{roleId});

        if(menuIdList.size() == 0){
            return ;
        }

        //保存角色与菜单关系
        for(Long menuId : menuIdList){
            SysRoleMenuDO sysRoleMenuEntity = new SysRoleMenuDO();
            sysRoleMenuEntity.setMenuId(menuId);
            sysRoleMenuEntity.setRoleId(roleId);

            sysRoleMenuRepository.save(sysRoleMenuEntity);
        }
    }

    @Override
    public List<Long> queryMenuIdList(Long roleId) {
    return sysRoleMenuRepository.findAllByMenuId(roleId).stream().map(SysRoleMenuDO::getRoleId).collect(Collectors.toList());
    }

    @Override
    public int deleteBatch(Long[] roleIds){
        int i = 0;
        for (Long roleId : roleIds) {
             i =i+ sysRoleMenuRepository.deleteAllByRoleId(roleId);
        }
        return i;
    }
}
