package beer.o2.modules.sys.dao;

import beer.o2.modules.sys.domain.user.entity.SysUserDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author:bomber
 * @Date:Created in 4:50 下午 2020/5/18
 * @Description:
 * @Modified By:
 */
public interface SysUserRepository extends JpaRepository<SysUserDO,Long> {
}
