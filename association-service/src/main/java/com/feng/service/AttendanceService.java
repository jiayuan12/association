package com.feng.service;

import com.feng.entity.Attendance;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface AttendanceService {
    void signAttendance(Integer userId);

    void applyLeave(Integer userId, String remark);

    PageInfo<Attendance> pageAttendance(Integer pageNum, Integer pageSize);

    PageInfo<Attendance> searchAttendance(String name, String className, String status, Integer pageNum, Integer pageSize);

    void resetAll();

    void batchDeleteAttendance(String name, String className, String grade);
}
