package beer.o2.modules.sys.domain.role.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author:bomber
 * @Date:Created in 3:47 下午 2020/5/18
 * @Description: 用户与角色对应关系
 * @Modified By:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sys_user_role")
public class SysUserRoleDO {

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

    @Column(columnDefinition = "bigint(20) comment '用户ID'")
    private Long userId;

    @Column(columnDefinition = "bigint(20) comment '角色ID'")
    private Long roleId;


}
