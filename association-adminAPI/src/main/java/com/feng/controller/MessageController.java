package com.feng.controller;

import com.feng.entity.Message;
import com.feng.entity.ResponseResult;
import com.feng.enums.ErrorEnum;
import com.feng.exception.ParamInvalidException;
import com.feng.service.MessageService;
import com.feng.util.ResponseResultUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 留言板管理接口
 * </p>
 *
 * @author rf
 * @since 2024-12-19
 */
@RestController
@CrossOrigin
@RequestMapping("/messages")
@Api(tags = "社团管理系统后台留言板管理接口", value = "社团管理系统后台留言板管理接口")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @ApiOperation(value = "根据条件分页查询所有留言", notes = "根据条件分页查询所有留言")
    @GetMapping
    public ResponseResult list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            Message search) {
        PageInfo<Message> messagePageInfo = messageService.getPage(pageNum, pageSize, search);
        return ResponseResultUtil.renderSuccess(messagePageInfo);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "通过id删除一个留言", notes = "通过id删除一个留言")
    public ResponseResult delete(@PathVariable("id") Long id) {
        messageService.deleteById(id);
        return ResponseResultUtil.renderSuccess(id);
    }

    @PutMapping("/{id}/publish")
    @ApiOperation(value = "发布/取消发布留言", notes = "发布/取消发布留言")
    public ResponseResult togglePublish(@PathVariable("id") Long id) {
        messageService.togglePublish(id);
        return ResponseResultUtil.renderSuccess("留言状态更新成功");
    }
}