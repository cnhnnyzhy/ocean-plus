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
 * 标签分类
 * </p>
 *
 * @author yang.zhang
 * @since 2022-07-12
 */
@Getter
@Setter
@TableName("tag_category")
public class TagCategoryDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类描述
     */
    private String description;

    /**
     * 父级分类id
     */
    private Long parentId;

    /**
     * 排序序号
     */
    private Integer seq;

    /**
     * 是否在用
     */
    private Boolean isUsed;

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
