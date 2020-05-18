package beer.o2.modules.sys.dao;

import beer.o2.modules.sys.domain.pub.entity.SysRoleMenuDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author:bomber
 * @Date:Created in 4:49 下午 2020/5/18
 * @Description:
 * @Modified By:
 */
public interface SysRoleMenuRepository extends JpaRepository<SysRoleMenuDO,Long> {
}
