package com.ocean.ddd.repository.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 标签
 * </p>
 *
 * @author yang.zhang
 * @since 2022-07-12
 */
@Getter
@Setter
@TableName("tag")
public class TagDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 标签类型：1-动态标签  2-静态标签
     */
    private Integer type;

    /**
     * 排序序号，置顶后序号从0开始用负数
     */
    private Integer seq;

    /**
     * 是否在用
     */
    private Boolean isUsed;

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 可见范围：1-平台级可见，对租户不可见 2-租户公共，子租户可见 3-租户私有，子租户不可见
     */
    private Integer visibleScope;

    /**
     * 标注类型：1-系统自动标注 2-手工标注
     */
    private Integer operationType;

    /**
     * 标签值类型：1-枚举型 2-数值型 3-日期型 4-布尔型
     */
    private Integer valueType;

    /**
     * 标注对象：1-内容 2-用户
     */
    private Integer useTarget;

    /**
     * 置顶时间
     */
    private LocalDateTime topTime;

    /**
     * 是否删除
     */
    private Boolean isDeleted;

    /**
     * 创建人
     */
    private Long createdBy;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新人
     */
    private Long updatedBy;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    /**
     * B端用户id
     */
    private Long businessUid;

    /**
     * 业务线id：2.saas 4.知客
     */
    private Integer bizId;

    /**
     * 业务线应用ID
     */
    private String bizApplicationId;


}
