package com.feng.service.impl;

import com.feng.dao.EquipmentMapper;
import com.feng.dao.FileMapper;
import com.feng.entity.Equipment;
import com.feng.entity.File;
import com.feng.service.EquipmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentMapper equipmentMapper;

    @Autowired
    private FileMapper fileMapper;

    /**
     * 分页查询设备列表
     */
    public PageInfo<Equipment> getPage(Integer pageNum, Integer pageSize, Equipment equipment) {
        PageHelper.startPage(pageNum, pageSize);
        List<Equipment> list = equipmentMapper.getList(equipment);
        return new PageInfo<>(list);
    }

    /**
     * 根据ID获取设备详情
     */
    public Equipment getById(Integer id) {
        return equipmentMapper.getById(id);
    }

    /**
     * 添加设备
     */
    public Equipment add(Equipment equipment) {
        // 设置创建时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        equipment.setCreateTime(sdf.format(date));
        equipment.setIsDeleted("0"); // 默认未删除
        equipmentMapper.add(equipment);
        return equipment;
    }

    /**
     * 更新设备信息
     */
    public void update(Equipment equipment) {
        equipmentMapper.update(equipment);
    }

    /**
     * 删除设备（逻辑删除）
     */
    public void deleteById(Integer id) {
        Equipment equipment = new Equipment();
        equipment.setId(id);
        equipment.setIsDeleted("1"); // 逻辑删除标记
        equipmentMapper.updateDelete(equipment);
        //如果有图片还要删除图片
        File file = fileMapper.selectByEquipmentId(id);
        if (file != null){
            fileMapper.deleteById(file.getId());
        }
    }

    @Override
    public void updateOperationStatus(Equipment equipment) {
        equipmentMapper.updateOperation(equipment);
    }
}
