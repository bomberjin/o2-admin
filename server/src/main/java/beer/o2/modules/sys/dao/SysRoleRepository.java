package beer.o2.modules.sys.dao;

import beer.o2.modules.sys.domain.role.entity.SysRoleDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author:bomber
 * @Date:Created in 4:49 下午 2020/5/18
 * @Description:
 * @Modified By:
 */
public interface SysRoleRepository extends JpaRepository<SysRoleDO,Long> {

    List<SysRoleDO> findAllByCreateUserId(Long createUserId);

    Page<SysRoleDO> findAllByCreateUserId(Long createUserId, Pageable pageable);

}
