package com.feng.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.feng.entity.File;

import java.io.Serializable;

/**
 * <p>
 * Mapper 接口
 * </p>
 */
public interface FileMapper extends BaseMapper<File> {
    Integer save(File file);

    File selectByActivityId(Serializable id);

    File selectByEquipmentId(Integer id);

    File selectByPassageId(Serializable id);
}
