package com.feng.service;

import com.feng.entity.Equipment;
import com.github.pagehelper.PageInfo;

public interface EquipmentService {
    PageInfo<Equipment> getPage(Integer pageNum, Integer pageSize, Equipment equipment);

    Equipment getById(Integer id);

    Equipment add(Equipment equipment);

    void update(Equipment equipment);

    void deleteById(Integer id);

    void updateOperationStatus(Equipment equipment);
}
