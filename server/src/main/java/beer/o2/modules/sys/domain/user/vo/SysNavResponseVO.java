package beer.o2.modules.sys.domain.user.vo;

import beer.o2.modules.sys.domain.pub.entity.SysMenuDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

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
public class SysNavResponseVO {

    private List<SysMenuDO> menuList;

    private Set<String> permissions;


}
