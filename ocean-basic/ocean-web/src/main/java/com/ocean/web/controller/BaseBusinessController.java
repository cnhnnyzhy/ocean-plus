package com.ocean.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import static com.ocean.web.constant.WebConstants.BUSINESS_API_PREFIX;

/**
 * 面向B端用户的接口
 *
 * @author ocean
 * @date 2024/4/24
 */
@RequestMapping(value = BUSINESS_API_PREFIX)
public abstract class BaseBusinessController {
}
