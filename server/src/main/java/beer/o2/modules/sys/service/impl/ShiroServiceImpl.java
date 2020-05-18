package beer.o2.modules.sys.service.impl;

import beer.o2.modules.sys.dao.SysMenuRepository;
import beer.o2.modules.sys.dao.SysUserRepository;
import beer.o2.modules.sys.dao.SysUserTokenRepository;
import beer.o2.modules.sys.domain.pub.entity.SysMenuDO;
import beer.o2.modules.sys.domain.user.entity.SysUserDO;
import beer.o2.modules.sys.domain.user.entity.SysUserTokenDO;
import beer.o2.modules.sys.service.ShiroService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author:bomber
 * @Date:Created in 5:18 下午 2020/5/18
 * @Description:
 * @Modified By:
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ShiroServiceImpl implements ShiroService {

    @Autowired
    private SysMenuRepository sysMenuRepository;

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysUserTokenRepository sysUserTokenRepository;



    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if(userId == 1){
            List<SysMenuDO> menuList = sysMenuRepository.findAll();
            permsList = new ArrayList<>(menuList.size());
            for(SysMenuDO menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else{
            permsList = sysMenuRepository.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public SysUserTokenDO queryByToken(String token) {
        return sysUserTokenRepository.findByToken(token);
    }

    @Override
    public SysUserDO queryUser(Long userId) {
        return sysUserRepository.findById(userId).get();
    }
}
