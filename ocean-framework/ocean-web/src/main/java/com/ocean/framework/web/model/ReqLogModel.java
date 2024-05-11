package com.ocean.framework.web.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 请求日志, 功能移动到 vhall-web-springboot-starter
 *
 * @author Administrator
 */
@Setter
@Getter
@ToString
public class ReqLogModel implements Serializable {
    /**
     * 方法名
     */
    private String method;
    /**
     * 请求地址
     */
    private String url;

    /**
     * 请求地址 处理方法
     */
    private String urlMatch;
    /**
     * 请求参数
     */
    private Object args;
    /**
     * 响应结果
     */
    private Object result;

    /**
     * 操作开始时间
     */
    private Date begin;

    /**
     * 操作结束时间
     */
    private Date ends;


    private Integer duration;

}
