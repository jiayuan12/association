package com.feng.controller;


import com.feng.entity.NewUserType;
import com.feng.entity.ResponseResult;
import com.feng.service.NewUserTypeService;
import com.feng.util.ResponseResultUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 */
@RestController
@RequestMapping("/newUserTypes")
@Api(tags = "社团管理系统前台社团类型接口")
public class NewUserTypeController {
    @Autowired
    private NewUserTypeService newUserTypeService;
    @GetMapping
    public ResponseResult list() {
        List<NewUserType> newUserTypeList = newUserTypeService.selectList(null);
        return ResponseResultUtil.renderSuccess(newUserTypeList);
    }
}

