package beer.o2.modules.sys.domain.pub.vo;

import beer.o2.modules.sys.domain.pub.entity.SysMenuDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author:bomber
 * @Date:Created in 8:54 下午 2020/5/26
 * @Description:
 * @Modified By:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysMenuResponseVO {

    List<SysMenuDO> menuList;

    SysMenuDO menu;
}
