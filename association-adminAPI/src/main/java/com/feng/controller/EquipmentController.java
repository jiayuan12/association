package com.feng.controller;


import com.feng.entity.Equipment;
import com.feng.entity.ResponseResult;
import com.feng.service.EquipmentService;
import com.feng.util.ResponseResultUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.apache.poi.ss.formula.functions.Now;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/equipments")
@Api(value = "协会管理系统后台设备管理接口",tags = "协会管理系统后台设备管理接口")
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;

    /**
     * 分页查询设备列表
     */
    @GetMapping("/page")
    public ResponseResult getPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                  @RequestParam(required = false) String name,
                                  @RequestParam(required = false) String needRepair,
                                  @RequestParam(required = false) String useStatus) {
        Equipment equipment = new Equipment();
        equipment.setName(name);
        equipment.setNeedRepair(needRepair);
        equipment.setUseStatus(useStatus);

        PageInfo<Equipment> pageInfo = equipmentService.getPage(pageNum, pageSize, equipment);
        return ResponseResultUtil.renderSuccess(pageInfo);
    }

    /**
     * 根据ID获取设备详情
     */
    @GetMapping("/{id}")
    public ResponseResult getById(@PathVariable Integer id) {
        Equipment equipment = equipmentService.getById(id);
        return ResponseResultUtil.renderSuccess(equipment);
    }

    /**
     * 添加设备
     */
    @PostMapping
    public ResponseResult add(@RequestBody Equipment equipment ) {
        equipmentService.add(equipment);
        return ResponseResultUtil.renderSuccess(equipment.getId());
    }

    /**
     * 更新设备信息
     */
    @PutMapping
    public ResponseResult update(@RequestBody Equipment equipment) {
        equipmentService.update(equipment);
        return ResponseResultUtil.renderSuccess("更新成功");
    }

    /**
     * 更新设备操作状态
     */
    @PutMapping("/{id}/status")
    public ResponseResult updateOperationStatus(@PathVariable Integer id,
                                        @RequestBody Map<String, String> requestBody) {
        String operationStatus = requestBody.get("operationStatus");
        if (operationStatus == null || operationStatus.isEmpty()) {
            return ResponseResultUtil.renderError(2001,"操作状态不能为空");
        }

        Equipment equipment = new Equipment();
        equipment.setId(id);
        equipment.setOperationStatus(operationStatus);
        if (operationStatus.equals("使用") || operationStatus.equals("充电")){
            equipment.setUseTime(LocalDateTime.now());
        }else if (operationStatus.equals("未使用")){
            equipment.setUseTime(null);
        }

        equipmentService.updateOperationStatus(equipment);
        return ResponseResultUtil.renderSuccess("状态更新成功");
    }
        /**
         * 删除设备
         */
    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable Integer id) {
        equipmentService.deleteById(id);
        return ResponseResultUtil.renderSuccess("删除成功");
    }
}

