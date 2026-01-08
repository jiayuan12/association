package com.feng.dto;

import com.feng.entity.Activity;
import com.feng.entity.File;
import com.feng.entity.Passage;
import lombok.Data;

import java.util.List;

@Data
public class ActivityFileDto extends Activity{
    private List<File> fileList;
}
