package com.feng.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author rf
 * @since 2019-04-17
 */
@Data
public class NewUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 协会编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 新人名称
     */
    private String name;
    /**
     * 性别
     */
    private String sex;
    /**
     * 新人班级
     */
    private String className;
    /**
     * 年级
     */
    private String grade;
    /**
     * 成绩
     */
    private Integer score;
    /**
     * QQ账号
     */
    private String account;
    /**
     * 新人简介
     */
    private String introduce;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    /**
     * 社团类型id
     */
    private Integer userTypeId;
    /**
     * 社团状态
     */
    private Integer status;


    @Override
    public String toString() {
        return "NewUser{" +
        "id=" + id +
        ", name=" + name +
        ", sex=" + sex +
        ", className=" + className +
        ", grade=" + grade +
        ", score=" + score +
        ", account=" + account +
        ", introduce=" + introduce +
        ", createTime=" + createTime +
        ", userTypeId=" + userTypeId +
        ", status=" + status +
        "}";
    }
}
