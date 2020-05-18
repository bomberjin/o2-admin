package beer.o2.modules.sys.dao;

import beer.o2.modules.sys.domain.user.SysMenuDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author:bomber
 * @Date:Created in 3:57 下午 2020/5/18
 * @Description:
 * @Modified By:
 */
public interface SysMenuRepository extends JpaRepository<SysMenuDO,Long> {
}
