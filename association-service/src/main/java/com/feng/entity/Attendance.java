package com.feng.entity;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Attendance extends User implements Serializable {
    /**
     * 签到id
     */
    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 签到状态
     */
    private String status;

    /**
     * 签到时间
     */
    private LocalDateTime attendanceTime;

    /**
     * 签到备注
     */
    private String remark;

    @Override
    public String toString() {
        return "Attendance{" +
        "id=" + id +
        ", userId=" + userId +
        ", status=" + status +
        ", attendanceTime=" + attendanceTime +
        ", remark=" + remark +
        "}";
    }
}
