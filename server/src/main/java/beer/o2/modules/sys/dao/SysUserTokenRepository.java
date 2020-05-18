package beer.o2.modules.sys.dao;

import beer.o2.modules.sys.domain.user.entity.SysUserTokenDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author:bomber
 * @Date:Created in 4:51 下午 2020/5/18
 * @Description:
 * @Modified By:
 */
public interface SysUserTokenRepository extends JpaRepository<SysUserTokenDO,Long> {

    SysUserTokenDO findByToken(String token);
}
