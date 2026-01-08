package com.feng.vo;

import com.feng.entity.NewUser;
import com.feng.entity.NewUserType;
import com.feng.entity.File;
import lombok.Data;

import java.util.List;

@Data
public class NewUserInfoVo extends NewUser {
    private NewUserType newUserType;
    private List<File> fileList;
}
