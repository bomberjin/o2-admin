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
 * @Description: 角色
 * @Modified By:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sys_role")
public class SysRoleDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint(20) comment '主键id 自增'")
    private Long roleId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime default null comment '创建时间'")
    private Date gmtCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime default null comment '修改时间'")
    private Date gmtModified;

    @Column(columnDefinition = "varchar(255) default '' comment '角色名称'")
    private String roleName;

    @Column(columnDefinition = "varchar(255) default '' comment '备注'")
    private String remark;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime default null comment '创建时间'")
    private Date createTime;

    @Column(columnDefinition = "bigint(20) comment '创建者ID'")
    private Long createUserId;

}
