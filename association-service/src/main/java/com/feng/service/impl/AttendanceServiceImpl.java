package com.feng.service.impl;

import com.feng.dao.AttendanceMapper;
import com.feng.dao.UserMapper;
import com.feng.entity.Attendance;
import com.feng.service.AttendanceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceMapper attendanceMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void signAttendance(Integer userId) {
        // 检查用户是否存在
        if (userMapper.selectById(userId) == null) {
            throw new RuntimeException("用户不存在");
        }
        // 检查是否已签到或请假
        Attendance existingAttendance = attendanceMapper.selectByUserId(userId);
        if (existingAttendance != null &&
                ("signed".equals(existingAttendance.getStatus()) || "leave".equals(existingAttendance.getStatus()))) {
            throw new RuntimeException("该用户已签到或已请假");
        }
        attendanceMapper.update(userId,"signed", "正常签到");
    }

    @Override
    public void applyLeave(Integer userId, String remark) {
        // 检查用户是否存在
        if (userMapper.selectById(userId) == null) {
            throw new RuntimeException("用户不存在");
        }

        // 检查是否已签到
        Attendance existingAttendance = attendanceMapper.selectByUserId(userId);
        if (existingAttendance != null && "signed".equals(existingAttendance.getStatus())) {
            throw new RuntimeException("该用户已签到，不能申请请假");
        }

        attendanceMapper.update(userId,"leave", remark);

    }

    @Override
    public PageInfo<Attendance> pageAttendance(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Attendance> list = attendanceMapper.selectAllWithUser();
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<Attendance> searchAttendance(String name, String className, String status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        // 构建查询条件
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("className", className);
        params.put("status", status);

        List<Attendance> attendances = attendanceMapper.searchAttendance(params);

        return new PageInfo<>(attendances);
    }

    @Override
    public void resetAll() {
        // 获取昨天的日期时间
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        LocalDateTime yesterdayStart = yesterday.withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime yesterdayEnd = yesterday.withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String Start = yesterdayStart.format(formatter);
        String End = yesterdayEnd.format(formatter);


        // 查询当天所有签到记录
        List<Attendance> attendances = attendanceMapper.findByDateBetween(Start, End);

        if (attendances.isEmpty()) {
           return;
        }

        // 批量更新
        for (Attendance att : attendances) {
            att.setStatus("unsigned");
            att.setAttendanceTime(null);
            att.setRemark("");
        }

        attendanceMapper.updateBatchById(attendances);
    }

    @Override
    public void batchDeleteAttendance(String name, String className, String grade) {
        attendanceMapper.BatchDelete(name, className, grade);
    }
}