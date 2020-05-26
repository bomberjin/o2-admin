package beer.o2.modules.sys.service.impl;

import beer.o2.common.exception.RRException;
import beer.o2.common.util.PageUtils;
import beer.o2.modules.sys.dao.SysRoleRepository;
import beer.o2.modules.sys.domain.role.entity.SysRoleDO;
import beer.o2.modules.sys.service.SysRoleMenuService;
import beer.o2.modules.sys.service.SysRoleService;
import beer.o2.modules.sys.service.SysUserRoleService;
import beer.o2.modules.sys.service.SysUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author:bomber
 * @Date:Created in 9:27 下午 2020/5/25
 * @Description:
 * @Modified By:
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SysRoleServiceImpl implements SysRoleService {


    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    SysRoleRepository sysRoleRepository;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String roleName = (String)params.get("roleName");
        Long createUserId = (Long)params.get("createUserId");

        PageRequest request = PageRequest.of((int) params.get("page"), (int) params.get("size"));

        //TODO like 角色名没有实现

        Page<SysRoleDO> all = null;

        if(createUserId!=null){
            all = sysRoleRepository.findAllByCreateUserId(createUserId,request);
        }else{
            all=sysRoleRepository.findAll(request);
        }

        return new PageUtils(all.getContent(),(int)all.getTotalElements(),(int)params.get("size"),(int)params.get("Page"));
    }

    @Override
    public void saveRole(SysRoleDO role) {
        role.setCreateTime(new Date());
        sysRoleRepository.save(role);

        //检查权限是否越权
        checkPrems(role);

        //保存角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    public void update(SysRoleDO role) {
        sysRoleRepository.save(role);

        //检查权限是否越权
        checkPrems(role);

        //更新角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    public void deleteBatch(Long[] roleIds) {
        //删除角色

        for (Long roleId : roleIds) {
            sysRoleRepository.deleteById(roleId);
        }


        //删除角色与菜单关联
        sysRoleMenuService.deleteBatch(roleIds);

        //删除角色与用户关联
        sysUserRoleService.deleteBatch(roleIds);
    }


    @Override
    public List<Long> queryRoleIdList(Long createUserId) {
        return sysRoleRepository.findAllByCreateUserId(createUserId).stream().map(SysRoleDO::getRoleId).collect(Collectors.toList());
    }

    /**
     * 检查权限是否越权
     */
    private void checkPrems(SysRoleDO role){
        //如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
        if(role.getCreateUserId() == 1){
            return ;
        }

        //查询用户所拥有的菜单列表
        List<Long> menuIdList = sysUserService.queryAllMenuId(role.getCreateUserId());

        //判断是否越权
        if(!menuIdList.containsAll(role.getMenuIdList())){
            throw new RRException("新增角色的权限，已超出你的权限范围");
        }
    }
}
