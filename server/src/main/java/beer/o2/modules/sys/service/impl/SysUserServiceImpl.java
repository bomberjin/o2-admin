

package beer.o2.modules.sys.service.impl;

import beer.o2.common.exception.RRException;
import beer.o2.common.util.PageUtils;
import beer.o2.modules.sys.dao.SysUserRepository;
import beer.o2.modules.sys.domain.user.entity.SysUserDO;
import beer.o2.modules.sys.service.SysRoleService;
import beer.o2.modules.sys.service.SysUserRoleService;
import beer.o2.modules.sys.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 系统用户
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    SysUserRepository sysUserRepository;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String username = (String) params.get("username");
        Long createUserId = (Long) params.get("createUserId");
        PageRequest request = PageRequest.of((int) params.get("page"), (int) params.get("size"));

        //TODO like 用户名没有实现
        Page<SysUserDO> allByCreateUserId = null;
        if (createUserId != null) {
            allByCreateUserId = sysUserRepository.findAllByCreateUserId(createUserId, request);
        } else {
            allByCreateUserId = sysUserRepository.findAll(request);
        }


        return new PageUtils(allByCreateUserId.getContent(), (int) allByCreateUserId.getTotalElements(), (int) params.get("size"), (int) params.get("page"));
    }

    @Override
    public List<String> queryAllPerms(Long userId) {
        return sysUserRepository.queryAllPerms(userId);
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return sysUserRepository.queryAllMenuId(userId);
    }

    @Override
    public SysUserDO queryByUserName(String username) {
        return sysUserRepository.findByUsername(username);
    }

    @Override
    public void saveUser(SysUserDO user) {
        user.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
        user.setSalt(salt);

        sysUserRepository.save(user);

        //检查角色是否越权
        checkRole(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    public void update(SysUserDO user) {
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
        }
        sysUserRepository.save(user);

        //检查角色是否越权
        checkRole(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    public void deleteBatch(Long[] userId) {
        for (Long aLong : userId) {
            sysUserRepository.deleteById(aLong);
        }
    }

    @Override
    public boolean updatePassword(Long userId, String password, String newPassword) {

        Optional<SysUserDO> byUserIdAndPassword = sysUserRepository.findByUserIdAndPassword(userId, password);

        if (!byUserIdAndPassword.isPresent()) {
            return false;
        }
        SysUserDO sysUserDO = byUserIdAndPassword.get();
        sysUserDO.setPassword(newPassword);
        sysUserRepository.save(sysUserDO);
        return true;
    }

    @Override
    public SysUserDO getById(Long userId) {
        return sysUserRepository.getOne(userId);
    }

    /**
     * 检查角色是否越权
     */
    private void checkRole(SysUserDO user) {
        if (user.getRoleIdList() == null || user.getRoleIdList().size() == 0) {
            return;
        }
        //如果不是超级管理员，则需要判断用户的角色是否自己创建
        if (user.getCreateUserId() == 1) {
            return;
        }

        //查询用户创建的角色列表
        List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());

        //判断是否越权
        if (!roleIdList.containsAll(user.getRoleIdList())) {
            throw new RRException("新增用户所选角色，不是本人创建");
        }
    }
}