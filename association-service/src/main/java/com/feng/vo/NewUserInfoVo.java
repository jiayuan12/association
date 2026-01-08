package com.feng.vo;

import com.feng.entity.NewUser;
import com.feng.entity.NewUserType;
import com.feng.entity.File;
import lombok.Data;

import java.util.List;

/**
 * Created by rf on 2019/5/3.
 */
@Data
public class NewUserInfoVo extends NewUser {
    private NewUserType newUserType;
    private List<File> fileList;
}
