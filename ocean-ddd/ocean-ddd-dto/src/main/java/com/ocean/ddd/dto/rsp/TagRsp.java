package com.ocean.ddd.dto.rsp;

import com.ocean.ddd.dto.Rsp;
import lombok.Data;

/**
 * @Description: TagRsp
 * @Author: yang.zhang
 * @Date: 2022/7/12 14:48
 */
@Data
public class TagRsp extends Rsp {
    private Long id;
    private String name;
}
