package beer.o2.modules.sys.domain.role.vo;

import beer.o2.common.util.PageUtils;
import beer.o2.modules.sys.domain.role.entity.SysRoleDO;
import beer.o2.modules.sys.domain.user.entity.SysUserDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * @Author:bomber
 * @Date:Created in 12:08 下午 2020/5/26
 * @Description:
 * @Modified By:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleResponseVO {

    SysRoleDO role;
    PageUtils page;
    List<SysRoleDO> list;


}
