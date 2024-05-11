package com.ocean.framework.web.model;

import lombok.Data;
import lombok.ToString;

/**
 * @author yongzheng.xu
 * @since 2021/6/8 14:25
 */
@Data
@ToString
public class AccessLogModel extends ReqLogModel {

    /**
     * 用户编号
     */
    private Long uid;
    /**
     * 用户类型
     */
    private Integer utype;

    /**
     * 用户 IP
     */
    private String clientIP;


}