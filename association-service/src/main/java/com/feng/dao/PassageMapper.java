package com.feng.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.feng.dto.PassageTypeDto;
import com.feng.dto.PassageFileDto;
import com.feng.entity.Passage;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 */
public interface PassageMapper extends BaseMapper<Passage> {
    List<PassageTypeDto> findPassage(Passage passage);

    PassageFileDto getInfoById(Integer id);

    Passage findSimplePassage(Passage passage);

    int add(Passage passage);
}
