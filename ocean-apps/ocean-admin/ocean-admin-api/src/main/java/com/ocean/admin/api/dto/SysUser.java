package com.ocean.admin.api.dto;

import com.ocean.common.core.dto.DTO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统用户
 *
 * @author ocean
 * @date 2022/11/15
 */
@Data
public class SysUser extends DTO {
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 加密盐值（随机）
     */
    private String salt;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 微信登录openId
     */
    private String wechatOpenId;

    /**
     * 是否锁定：0-否 1-是
     */
    private String isLocked;

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
