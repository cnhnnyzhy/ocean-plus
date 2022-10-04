package com.ocean.admin.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author ocean
 * @since 2022-10-05
 */
@Getter
@Setter
@TableName("t_sys_menu")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 前端路径
     */
    private String path;

    /**
     * 图标
     */
    private String icon;

    /**
     * 类型
     */
    private Boolean type;

    /**
     * 排序序号
     */
    private Integer seq;

    /**
     * 是否删除：0-否 1-是
     */
    private String isDeleted;

    /**
     * 创建人id
     */
    private Long createdBy;

    /**
     * 修改人id
     */
    private Long updatedBy;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    /**
     * 租户id
     */
    private Long tenantId;


}
