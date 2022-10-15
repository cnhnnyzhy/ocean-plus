package com.ocean.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户与角色关联表
 * </p>
 *
 * @author ocean
 * @since 2022-10-05
 */
@Getter
@Setter
@TableName("t_sys_user_rule")
public class SysUserRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Long ruleId;


}
