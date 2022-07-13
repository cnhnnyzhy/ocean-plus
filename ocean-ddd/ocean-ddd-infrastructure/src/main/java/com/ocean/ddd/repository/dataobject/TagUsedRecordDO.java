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
 * 标签使用记录
 * </p>
 *
 * @author yang.zhang
 * @since 2022-07-12
 */
@Getter
@Setter
@TableName("tag_used_record")
public class TagUsedRecordDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 使用方id
     */
    private String sourceId;

    /**
     * 使用方类型：1-直播 2-用户 3-内容
     */
    private Integer sourceType;

    /**
     * 标签id
     */
    private Long tagId;

    /**
     * 标记来源：1-系统标记 2-人工标记
     */
    private Integer markSource;

    /**
     * 是否删除
     */
    private Boolean isDeleted;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    /**
     * 更新人
     */
    private Long updatedBy;

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
