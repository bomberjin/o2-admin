package beer.o2.modules.sys.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author:bomber
 * @Date:Created in 3:47 下午 2020/5/18
 * @Description: 系统用户
 * @Modified By:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sys_user")
public class SysUserDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint(20) comment '主键id 自增'")
    private Long userId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime default null comment '创建时间'")
    private Date gmtCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime default null comment '修改时间'")
    private Date gmtModified;

    @Column(columnDefinition = "varchar(255) default '' comment '用户名'")
    private String username;

    @Column(columnDefinition = "varchar(255) default '' comment '密码'")
    private String password;

    @Column(columnDefinition = "varchar(255) default '' comment '盐'")
    private String salt;

    @Column(columnDefinition = "varchar(255) default '' comment '邮箱'")
    private String email;

    @Column(columnDefinition = "varchar(255) default '' comment '手机号'")
    private String mobile;

    @Column(columnDefinition = "int(4) default 0 comment '状态  0：禁用   1：正常'")
    private Integer status;

    @Column(columnDefinition = "bigint(20) comment '创建者ID'")
    private Long createUserId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime default null comment '创建时间'")
    private Date createTime;


}
