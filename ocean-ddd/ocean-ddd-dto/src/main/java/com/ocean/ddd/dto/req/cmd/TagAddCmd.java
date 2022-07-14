package com.ocean.ddd.dto.req.cmd;

import com.ocean.ddd.dto.Cmd;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @Description: 标签增加命令
 * @Author: yang.zhang
 * @Date: 2022/7/12 15:32
 */
@Data
public class TagAddCmd extends Cmd {
    @NotBlank(message = "参数[name]不能为空")
    @Length(max = 100, message = "参数[name]值过长")
    private String name;
}
