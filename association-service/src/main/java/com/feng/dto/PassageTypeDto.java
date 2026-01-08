package com.feng.dto;

import com.feng.entity.Passage;
import com.feng.entity.PassageType;
import lombok.Data;

@Data
public class PassageTypeDto extends Passage{
    private PassageType passageType;
}
