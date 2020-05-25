package beer.o2.modules.sys.dao;

import beer.o2.modules.sys.domain.user.entity.SysUserDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @Author:bomber
 * @Date:Created in 4:50 下午 2020/5/18
 * @Description:
 * @Modified By:
 */
public interface SysUserRepository extends JpaRepository<SysUserDO,Long> {

    Optional<SysUserDO> findByUserIdAndPassword(Long userId,String password);

    /**
     * 查询用户的所有权限
     * @param userId  用户ID
     */
    @Query(value = "SELECT m.perms FROM sys_user_role ur LEFT JOIN sys_role_menu rm ON ur.role_id = rm.role_id LEFT JOIN sys_menu m ON rm.menu_id = m.menu_id WHERE ur.user_id = ?1" , nativeQuery = true)
    List<String> queryAllPerms(Long userId);

    /**
     * 查询用户的所有菜单ID
     */
    @Query(value = "SELECT DISTINCT rm.menu_id FROM sys_user_role ur LEFT JOIN sys_role_menu rm ON ur.role_id = rm.role_id WHERE ur.user_id = ?1" , nativeQuery = true)
    List<Long> queryAllMenuId(Long userId);

    /**
     * 根据用户名，查询系统用户
     */
    SysUserDO findByUsername(String username);

    Page<SysUserDO> findAllByCreateUserId(Long createUserId, Pageable pageable);
}
