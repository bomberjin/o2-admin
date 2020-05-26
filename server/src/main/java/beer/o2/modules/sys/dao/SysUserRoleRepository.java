package beer.o2.modules.sys.dao;

import beer.o2.modules.sys.domain.role.entity.SysUserRoleDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author:bomber
 * @Date:Created in 4:50 下午 2020/5/18
 * @Description:
 * @Modified By:
 */
public interface SysUserRoleRepository extends JpaRepository<SysUserRoleDO,Long> {

    void deleteAllByUserId(Long userId);

    List<SysUserRoleDO> findAllByUserId(Long userId);

    int deleteAllByRoleId(Long roleId);
}
