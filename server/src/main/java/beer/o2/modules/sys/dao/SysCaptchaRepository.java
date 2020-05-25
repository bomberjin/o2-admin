package beer.o2.modules.sys.dao;

import beer.o2.modules.sys.domain.pub.entity.SysCaptchaDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Author:bomber
 * @Date:Created in 4:49 下午 2020/5/18
 * @Description:
 * @Modified By:
 */
public interface SysCaptchaRepository extends JpaRepository<SysCaptchaDO,Long> {
    Optional<SysCaptchaDO> findByUuid(String uuid);
}
