package com.feng.controller;


import com.feng.entity.NewUserType;
import com.feng.entity.ResponseResult;
import com.feng.enums.ErrorEnum;
import com.feng.exception.ParamInvalidException;
import com.feng.service.NewUserTypeService;
import com.feng.util.ResponseResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/newUserTypes")
@Api(value = "协会管理系统后台新人类型管理接口",tags = "协会管理系统后台新人类型管理接口")
public class NewUserTypeController {
    @Autowired
    private NewUserTypeService newUserTypeService;

    @GetMapping
    @ApiOperation("查找所有新人类型")
    public ResponseResult list() {
        List<NewUserType> newUserTypeList = newUserTypeService.selectList(null);
        return ResponseResultUtil.renderSuccess(newUserTypeList);
    }

    @ApiOperation("通过id获取一个新人类型")
    @GetMapping("/{id}")
    public ResponseResult getById(@PathVariable("id") Integer id) {
        NewUserType newUserType = newUserTypeService.selectById(id);
        return ResponseResultUtil.renderSuccess(newUserType);
    }

    @PutMapping
    @ApiOperation("更新社团类型")
    public ResponseResult edit(@Valid @RequestBody NewUserType newUserType, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String msg = bindingResult.getFieldError().getDefaultMessage();
            throw new ParamInvalidException(ErrorEnum.INVALIDATE_PARAM_EXCEPTION.setMsg(msg));
        }
        newUserTypeService.updateById(newUserType);
        return ResponseResultUtil.renderSuccess("更新成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("根据id删除新人类型")
    public ResponseResult delete(Integer id) {
        newUserTypeService.deleteById(id);
        return ResponseResultUtil.renderSuccess("删除社团类型成功");
    }

    @PostMapping
    @ApiOperation("添加新人类型")
    public ResponseResult add(@Valid @RequestBody NewUserType newUserType, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String msg = bindingResult.getFieldError().getDefaultMessage();
            throw new ParamInvalidException(ErrorEnum.INVALIDATE_PARAM_EXCEPTION.setMsg(msg));
        }
        newUserTypeService.insert(newUserType);
        return ResponseResultUtil.renderSuccess("添加新人类型成功");
    }
}

