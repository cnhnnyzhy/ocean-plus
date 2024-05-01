package com.ocean.framework.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import static com.ocean.framework.web.constant.WebConstants.ADMIN_API_PREFIX;

/**
 * 面向Admin管理端用户的接口
 *
 * @author ocean
 * @date 2024/4/24
 */
@RequestMapping(value = ADMIN_API_PREFIX)
public abstract class BaseAdminController {
}
