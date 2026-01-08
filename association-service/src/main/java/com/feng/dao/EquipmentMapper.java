package com.feng.dao;

import com.feng.entity.Equipment;

import java.util.List;

public interface EquipmentMapper {
    List<Equipment> getList(Equipment equipment);

    Equipment getById(Integer id);

    int add(Equipment equipment);

    int deleteById(Integer id);

    void updateOperation(Equipment equipment);

    void updateDelete(Equipment equipment);

    void update(Equipment equipment);
}
