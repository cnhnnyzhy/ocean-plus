package com.ocean.ddd.dto.rsp;

import com.ocean.ddd.dto.Rsp;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description: TagRsp
 * @Author: yang.zhang
 * @Date: 2022/7/12 14:48
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TagRsp extends Rsp {
    private Long id;
    private String name;
}
