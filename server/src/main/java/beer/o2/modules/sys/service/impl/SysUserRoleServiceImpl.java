

package beer.o2.modules.sys.service.impl;


import beer.o2.modules.sys.dao.SysUserRoleRepository;
import beer.o2.modules.sys.domain.role.entity.SysUserRoleDO;
import beer.o2.modules.sys.service.SysUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 用户与角色对应关系
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Autowired
    SysUserRoleRepository sysUserRoleRepository;

    @Override
    public void saveOrUpdate(Long userId, List<Long> roleIdList) {
        //先删除用户与角色关系
        sysUserRoleRepository.deleteAllByUserId(userId);

        if (roleIdList == null || roleIdList.size() == 0) {
            return;
        }

        //保存用户与角色关系
        for (Long roleId : roleIdList) {
            SysUserRoleDO sysUserRoleEntity = new SysUserRoleDO();
            sysUserRoleEntity.setUserId(userId);
            sysUserRoleEntity.setRoleId(roleId);

            sysUserRoleRepository.save(sysUserRoleEntity);
        }
    }

    @Override
    public List<Long> queryRoleIdList(Long userId) {
        return sysUserRoleRepository.findAllByUserId(userId).stream().map(SysUserRoleDO::getRoleId).collect(Collectors.toList());
    }

    @Override
    public int deleteBatch(Long[] roleIds) {
        int i = 0;
        for (Long roleId : roleIds) {
           i=i+ sysUserRoleRepository.deleteAllByRoleId(roleId);
        }
        return i;
    }
}
