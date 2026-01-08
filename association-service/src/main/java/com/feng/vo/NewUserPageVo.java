package com.feng.vo;

import com.feng.entity.NewUser;
import com.feng.entity.NewUserType;
import com.github.pagehelper.PageInfo;
import lombok.Data;

@Data
public class NewUserPageVo {
    private NewUserType newUserType;
    private PageInfo<NewUser> clubPageInfo;

    public NewUserPageVo(NewUserType newUserType, PageInfo<NewUser> clubPageInfo) {
        this.newUserType = newUserType;
        this.clubPageInfo = clubPageInfo;
    }

    public NewUserPageVo() {
    }

}
