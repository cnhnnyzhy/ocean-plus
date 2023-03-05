package com.ocean.ddd.module.controller;

import com.ocean.ddd.common.Result;
import com.ocean.ddd.dto.req.cmd.TagAddCmd;
import com.ocean.ddd.dto.req.qry.TagGetListQry;
import com.ocean.ddd.dto.rsp.TagRsp;
import com.ocean.ddd.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @Description: 标签接口
 * @Author: yang.zhang
 * @Date: 2022/7/12 15:30
 */
@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @PostMapping("add")
    public Result<TagRsp> add(@Valid @RequestBody TagAddCmd req) {
        TagRsp tagRsp = new TagRsp();
        Optional.ofNullable(tagService.add(req)).ifPresent(obj -> tagRsp.setId(obj.getId()));
        return Result.success(tagRsp);
    }

    @PostMapping("list")
    public Result<List<TagRsp>> getList(@Valid @RequestBody TagGetListQry req) {
        return Result.success(tagService.getList(req));
    }
}
