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
 * @Description: 系统用户Token
 * @Modified By:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sys_user_token")
public class SysUserTokenDO {

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

    @Column(columnDefinition = "bigint(20) comment ''")
    private Long userId;

    @Column(columnDefinition = "varchar(255) default '' comment 'token'")
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime default null comment '过期时间'")
    private Date expireTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime default null comment '更新时间'")
    private Date updateTime;


}
