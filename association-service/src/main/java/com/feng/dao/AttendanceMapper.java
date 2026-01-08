package com.feng.dao;

import com.feng.entity.Attendance;
import com.github.pagehelper.PageInfo;
import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.models.auth.In;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AttendanceMapper {
    Attendance selectByUserId(Integer userId);

    void insert(Attendance attendance);

    void updateById(Attendance existingAttendance);

    List<Attendance> selectAllWithUser();

    List<Attendance> searchAttendance(Map<String, Object> params);

    void update(Integer userId, String status, String remark);

    void resetAll();

    List<Attendance> findByDateBetween(String startTime, String endTime);

    void updateBatchById(List<Attendance> attendances);

    void BatchDelete(
            @Param("name") String name,
            @Param("className") String className,
            @Param("grade") String grade
    );

    void deleteById(Integer id);
}
