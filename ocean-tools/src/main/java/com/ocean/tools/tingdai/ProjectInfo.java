package com.ocean.tools.tingdai;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 项目信息
 * @Author: yang.zhang
 * @Date: 2022/7/15 06:32
 */
@NoArgsConstructor
@Data
public class ProjectInfo implements Serializable {
    /**
     * 项目名称
     */
    private String name;
    /**
     * 通知时间
     */
    private String noticeDate;
    /**
     * 执行时间
     */
    private String executeDate;
    /**
     * 城市
     */
    private String city;
    /**
     * 省份
     */
    private String province;
    /**
     * 创建时间
     */
    private String createTime;

    public ProjectInfo(String name, String noticeDate, String executeDate, String city, String province, String createTime) {
        this.name = name;
        this.noticeDate = noticeDate;
        this.executeDate = executeDate;
        this.city = city;
        this.province = province;
        this.createTime = createTime;
    }
}
