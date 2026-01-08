package com.feng.controller;

import com.feng.dto.ActivityTypeDto;
import com.feng.entity.Attendance;
import com.feng.entity.ResponseResult;
import com.feng.enums.ErrorEnum;
import com.feng.service.AttendanceService;
import com.feng.util.ResponseResultUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import lombok.var;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attendances")
@Api(value = "协会管理系统后台每日签到管理接口",tags = "协会管理系统后台每日签到管理接口")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // 签到接口
    @PostMapping("/sign")
    public ResponseResult signAttendance(@RequestBody Map<String, Object> params) {
        Integer userId = (Integer) params.get("userId");
        attendanceService.signAttendance(userId);
        return ResponseResultUtil.renderSuccess("签到成功");
    }

    // 请假接口
    @PostMapping("/leave")
    public ResponseResult applyLeave(@RequestBody Map<String, Object> params) {
        Integer userId = (Integer) params.get("userId");
        String remark = (String) params.get("remark");
        attendanceService.applyLeave(userId, remark);
        return ResponseResultUtil.renderSuccess("请假申请成功");
    }

    // 重置所有签到记录
    @PostMapping("/reset")
    public ResponseResult resetAttendance() {
        try {
            attendanceService.resetAll();
            return ResponseResultUtil.renderSuccess("重置成功");
        }catch (Exception e){
            return ResponseResultUtil.renderError(ErrorEnum.RESET_ERROR);
        }
    }

    // 分页查询所有签到记录
    @GetMapping("/page")
    public ResponseResult pageAttendance(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Attendance> attendancePageInfo = attendanceService.pageAttendance(pageNum, pageSize);
        return ResponseResultUtil.renderSuccess(attendancePageInfo);
    }

    // 条件查询签到记录
    @GetMapping("/search")
    public ResponseResult searchAttendance(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String className,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Attendance> attendancePageInfo = attendanceService.searchAttendance(name, className, status, pageNum, pageSize);
        return ResponseResultUtil.renderSuccess(attendancePageInfo);
    }

    @GetMapping("/export")
    public void exportAttendance(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String className,
            @RequestParam(required = false) String status,
            HttpServletResponse response) throws IOException {
        try {
            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" +
                    java.net.URLEncoder.encode("每日签到表.xlsx", "UTF-8"));

            // 获取数据
            PageInfo<Attendance> attendanceList = attendanceService.searchAttendance(name, className, status, 1, 9999);

            // 创建Excel工作簿
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("每日签到表");

            // 创建表头
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("姓名");
            headerRow.createCell(1).setCellValue("性别");
            headerRow.createCell(2).setCellValue("班级");
            headerRow.createCell(3).setCellValue("年级");
            headerRow.createCell(4).setCellValue("签到状态");
            headerRow.createCell(5).setCellValue("签到时间");
            headerRow.createCell(6).setCellValue("备注");

            // 创建左对齐样式
            CellStyle leftAlignStyle = workbook.createCellStyle();
            leftAlignStyle.setAlignment(HorizontalAlignment.LEFT);

            // 填充数据
            for (int i = 0; i < attendanceList.getList().size(); i++) {
                Attendance attendance = attendanceList.getList().get(i);
                Row row = sheet.createRow(i + 1);

                // 姓名
                Cell cell0 = row.createCell(0);
                cell0.setCellValue(attendance.getName());
                cell0.setCellStyle(leftAlignStyle);

                // 性别
                Cell cell1 = row.createCell(1);
                String sexText = "未知";
                if (attendance.getSex() != 0) {
                    sexText = attendance.getSex() == 30007 ? "男" : (attendance.getSex() == 22899 ? "女" : "未知");
                }
                cell1.setCellValue(sexText);
                cell1.setCellStyle(leftAlignStyle);

                // 班级
                Cell cell2 = row.createCell(2);
                cell2.setCellValue(attendance.getClassName());
                cell2.setCellStyle(leftAlignStyle);

                // 年级
                Cell cell3 = row.createCell(3);
                cell3.setCellValue(attendance.getGrade());
                cell3.setCellStyle(leftAlignStyle);

                // 签到状态
                Cell cell4 = row.createCell(4);
                String statusText = "未知";
                if (attendance.getStatus() != null) {
                    statusText = attendance.getStatus().equals("signed") ? "已签到" :
                            attendance.getStatus().equals("unsigned") ? "未签到" : "请假";
                }
                cell4.setCellValue(statusText);
                cell4.setCellStyle(leftAlignStyle);

                // 签到时间
                Cell cell5 = row.createCell(5);
                cell5.setCellValue(attendance.getAttendanceTime() != null ?
                        attendance.getAttendanceTime().toString() : "");
                cell5.setCellStyle(leftAlignStyle);

                // 备注
                Cell cell6 = row.createCell(6);
                cell6.setCellValue(attendance.getRemark() != null ? attendance.getRemark() : "");
                cell6.setCellStyle(leftAlignStyle);
            }

            // 自动调整列宽
            for (int i = 0; i < 7; i++) {
                sheet.autoSizeColumn(i+30);
            }

            // 写入响应流
            workbook.write(response.getOutputStream());
            workbook.close();

        } catch (Exception e) {
            response.setStatus(500);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":1001,\"message\":\"系统繁忙，请稍后访问！\"}");
        }
    }

    @DeleteMapping("/batchDelete")
    public ResponseResult batchDeleteAttendance(@RequestBody Map<String, String> params) {
        String name = params.get("name");
        String className = params.get("className");
        String grade = params.get("grade");

        attendanceService.batchDeleteAttendance(name, className, grade);
        return ResponseResultUtil.renderSuccess("批量删除成功");
    }

}