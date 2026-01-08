package com.feng.vo;

import com.feng.entity.Activity;
import com.feng.entity.ActivityType;
import com.github.pagehelper.PageInfo;
import lombok.Data;

@Data
public class ActivityPageVo {
    private ActivityType activityType;
    private PageInfo<Activity> activityPageInfo;

    public ActivityPageVo(ActivityType activityType, PageInfo<Activity> activityPageInfo) {
        this.activityType = activityType;
        this.activityPageInfo = activityPageInfo;
    }

    public ActivityPageVo() {
    }

}
