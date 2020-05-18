package beer.o2.modules.sys.domain.pub.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author:bomber
 * @Date:Created in 3:47 下午 2020/5/18
 * @Description: 角色与菜单对应关系
 * @Modified By:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sys_role_menu")
public class SysRoleMenuDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint(20) comment '主键id 自增'")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime default null comment '创建时间'")
    private Date gmtCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime default null comment '修改时间'")
    private Date gmtModified;

    @Column(columnDefinition = "bigint(20) comment '菜单ID'")
    private Long menuId;

    @Column(columnDefinition = "bigint(20) comment '角色ID'")
    private Long roleId;


}
