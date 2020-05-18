package beer.o2.modules.sys.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author:bomber
 * @Date:Created in 3:47 下午 2020/5/18
 * @Description: 菜单管理
 * @Modified By:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sys_menu")
public class SysMenuDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint(20) comment '主键id 自增'")
    private Long menuId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime default null comment '创建时间'")
    private Date gmtCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime default null comment '修改时间'")
    private Date gmtModified;

    @Column(columnDefinition = "bigint(20) comment '父菜单ID，一级菜单为0'")
    private Long parentId;

    @Column(columnDefinition = "varchar(255) default '' comment '菜单名称'")
    private String name;
    @Column(columnDefinition = "varchar(255) default '' comment '菜单URL'")
    private String url;
    @Column(columnDefinition = "varchar(255) default '' comment '授权(多个用逗号分隔，如：user:list,user:create)'")
    private String perms;
    @Column(columnDefinition = "int(4) default 0 comment '类型   0：目录   1：菜单   2：按钮'")
    private Integer type;
    @Column(columnDefinition = "varchar(255) default '' comment '菜单图标'")
    private String icon;
    @Column(columnDefinition = "int(4) default 0 comment '排序'")
    private Integer orderNum;


}
