package beer.o2.modules.sys.dao;

import beer.o2.modules.sys.domain.pub.entity.SysMenuDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author:bomber
 * @Date:Created in 3:57 下午 2020/5/18
 * @Description:
 * @Modified By:
 */
public interface SysMenuRepository extends JpaRepository<SysMenuDO,Long> {

    @Query(value = "select m.perms from sys_user_role ur LEFT JOIN sys_role_menu rm on ur.role_id = rm.role_id LEFT JOIN sys_menu m on rm.menu_id = m.menu_id  where ur.user_id = ?1" , nativeQuery = true)
    List<String> queryAllPerms(Long userId);
}
