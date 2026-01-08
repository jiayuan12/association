package com.feng.controller;


import com.feng.entity.NewUser;
import com.feng.entity.ResponseResult;
import com.feng.enums.ErrorEnum;
import com.feng.exception.ParamInvalidException;
import com.feng.service.NewUserService;
import com.feng.util.ResponseResultUtil;
import com.feng.vo.NewUserInfoVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author rf
 * @since 2019-03-03
 */
@RestController
@CrossOrigin
@RequestMapping("/newUsers")
@Api(value = "协会管理系统后台新人管理接口",tags = "协会管理系统后台新人管理接口")
public class NewUserController {
    @Autowired
    private NewUserService newUserService;

    @ApiOperation("通过新人编号id查看")
    @GetMapping("/{id}")
    public ResponseResult get(@PathVariable("id") Integer id) throws Exception {
        NewUser newUser = newUserService.getById(id);
        return ResponseResultUtil.renderSuccess(newUser);
    }

    @ApiOperation("分页查询所有新人")
    @GetMapping("/page")
    public ResponseResult list(@RequestParam(defaultValue = "1") int pageNum,
                               @RequestParam(defaultValue = "10") int pageSize) {
        PageInfo<NewUserInfoVo> newUserPageInfo = newUserService.getPage(pageNum, pageSize);
        return ResponseResultUtil.renderSuccess(newUserPageInfo);
    }

    @PostMapping("/{id}/addToMembers")
    public ResponseResult addToMembers(@PathVariable Long id) {
        try {
            boolean success = newUserService.addToMembers(id);
            if (success) {
                return ResponseResultUtil.renderSuccess("添加成功");
            } else {
                return ResponseResultUtil.renderError(4010,"添加失败，用户不存在或已添加");
            }
        } catch (Exception e) {
            return ResponseResultUtil.renderError(4010,"添加失败：" + e.getMessage());
        }
    }

    /**
     * 根据条件批量删除新人
     */
    @DeleteMapping("/batchDeleteByCondition")
    public ResponseResult batchDeleteByCondition(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "className", required = false) String className,
            @RequestParam(value = "score", required = false) Integer score) {
        try {
            int count = newUserService.batchDeleteByCondition(name, className, score);
            return ResponseResultUtil.renderSuccess("成功删除 " + count + " 条记录");
        } catch (Exception e) {
            return ResponseResultUtil.renderError(4010,"删除失败：" + e.getMessage());
        }
    }

    @ApiOperation("根据条件分页查询新人")
    @GetMapping("/search")
    public ResponseResult search(NewUser search,
                                 @RequestParam(defaultValue = "1") int pageNum,
                                 @RequestParam(defaultValue = "10") int pageSize) {
        PageInfo<NewUserInfoVo> newUserPageInfo = newUserService.searchPage(pageNum, pageSize, search);
        return ResponseResultUtil.renderSuccess(newUserPageInfo);
    }

    @ApiOperation("统计各班级人数分布")
    @GetMapping("/statistics/class")
    public ResponseResult getClassStatistics() {
        List<Map<String, Object>> classStatistics = newUserService.getClassStatistics();
        return ResponseResultUtil.renderSuccess(classStatistics);
    }

    @ApiOperation("统计各成绩区间人数分布")
    @GetMapping("/statistics/score")
    public ResponseResult getScoreStatistics() {
        List<Map<String, Object>> scoreStatistics = newUserService.getScoreStatistics();
        return ResponseResultUtil.renderSuccess(scoreStatistics);
    }

    @ApiOperation("通过id删除一个社团")
    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable("id") Long id) {
        newUserService.deleteById(id);
        return ResponseResultUtil.renderSuccess(id);
    }

    @ApiOperation("添加一个新成员")
    @PostMapping
    public ResponseResult add(@Valid @RequestBody NewUser newUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String msg = bindingResult.getFieldError().getDefaultMessage();
            throw new ParamInvalidException(ErrorEnum.INVALIDATE_PARAM_EXCEPTION.setMsg(msg));
        }
        newUserService.add(newUser);
        return ResponseResultUtil.renderSuccess("添加成员成功");
    }

    @ApiOperation("通过id更新一个社团")
    @PutMapping
    public ResponseResult update(@RequestBody @Valid NewUser newUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String msg = bindingResult.getFieldError().getDefaultMessage();
            throw new ParamInvalidException(ErrorEnum.INVALIDATE_PARAM_EXCEPTION.setMsg(msg));
        }
        newUserService.updateWithId(newUser);
        return ResponseResultUtil.renderSuccess("更新社团成功");
    }


    @GetMapping("/export")
    public void exportNewUserScores(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String className,
            @RequestParam(required = false) Integer score,
            HttpServletResponse response) throws IOException {

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("新人成绩单.xlsx", "UTF-8"));

        // 查询数据
        List<NewUserInfoVo> newUserList = newUserService.searchNewUsers(name, className, score);

        // 创建Excel工作簿
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("新人成绩单");

        // 创建表头
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("姓名");
        headerRow.createCell(1).setCellValue("性别");
        headerRow.createCell(2).setCellValue("班级");
        headerRow.createCell(3).setCellValue("成绩");

        // 在创建单元格后设置样式
        CellStyle leftAlignStyle = workbook.createCellStyle();
        leftAlignStyle.setAlignment(HorizontalAlignment.LEFT);

        // 填充数据并设置左对齐
        for (int i = 0; i < newUserList.size(); i++) {
            NewUser user = newUserList.get(i);
            Row row = sheet.createRow(i + 1);
            Cell cell0 = row.createCell(0);
            cell0.setCellValue(user.getName());
            cell0.setCellStyle(leftAlignStyle);

            String sexText = "";
            if (!user.getSex().isEmpty()) {
                if (user.getSex().equals("男")){
                    sexText = "男";
                }else if (user.getSex().equals("女")){
                    sexText = "女";
                }else {
                    sexText = "未知";
                }
            }

            Cell cell1 = row.createCell(1);
            cell1.setCellValue(sexText);
            cell1.setCellStyle(leftAlignStyle);

            Cell cell2 = row.createCell(2);
            cell2.setCellValue(user.getClassName());
            cell2.setCellStyle(leftAlignStyle);

            Cell cell3 = row.createCell(3);
            cell3.setCellValue(user.getScore());
            cell3.setCellStyle(leftAlignStyle);
        }


        // 自动调整列宽
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i+15);
        }

        // 写入响应流
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}

