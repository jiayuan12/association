package com.feng.vo;

import com.feng.entity.User;
import lombok.Data;

@Data
public class LoginUserVo extends User{
    private String code;
}
