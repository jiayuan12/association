package com.feng.controller;


import com.feng.entity.NewUser;
import com.feng.entity.ResponseResult;
import com.feng.service.NewUserService;
import com.feng.util.ResponseResultUtil;
import com.feng.vo.NewUserPageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 前端控制器
 * </p>
 */
@RestController
@CrossOrigin
@RequestMapping("/clubs")
@Api(tags = "社团管理系统前台社团接口")
public class NewUserController {
    @Autowired
    private NewUserService newUserService;

    @GetMapping("/{id}")
    @ApiOperation("通过协会编号id查看")
    public ResponseResult get(@PathVariable("id") Integer id) throws Exception {
        NewUser newUser = newUserService.getById(id);
        return ResponseResultUtil.renderSuccess(newUser);
    }

    @GetMapping
    @ApiOperation("根据条件分页查询所有社团")
    public ResponseResult list(Integer newUserTypeId, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        NewUserPageVo newUserPageVo = newUserService.getPageWithTypeList(pageNum, pageSize, newUserTypeId);
        return ResponseResultUtil.renderSuccess(newUserPageVo);
    }

    /**
     * 添加新成员
     */
    @ApiOperation("添加一个新成员")
    @PostMapping
    public ResponseResult add(@Valid @RequestBody NewUser newUser) {
        try {
            newUserService.add(newUser);
            System.out.println("用户添加成功: " + newUser.getName());
            return ResponseResultUtil.renderSuccess("添加成员成功");
        } catch (Exception e) {
            System.err.println("添加失败: " + e.getMessage());
            e.printStackTrace();
            return ResponseResultUtil.renderError(4010,"添加失败：" + e.getMessage());
        }
    }

}

