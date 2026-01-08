package com.feng.vo;

import com.feng.entity.NewUser;
import com.feng.entity.NewUserType;
import com.github.pagehelper.PageInfo;
import lombok.Data;

/**
 * Created by rf on 2019/4/18.
 */
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
