package com.feng.vo;

import com.feng.entity.NewUser;
import com.feng.entity.NewUserType;
import lombok.Data;

import java.util.List;

@Data
public class NewUserVo {
    private NewUserType newUserType;
    private List<NewUser> clubList;

    public NewUserVo(NewUserType newUserType, List<NewUser> clubList) {
        this.newUserType = newUserType;
        this.clubList = clubList;
    }
}
