package com.feng.entity;

import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ibatis.annotations.Results;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * <p>
 * </p>
 */
@Data
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 活动名称
     */
    @NotNull(message = "活动名称不能为空")
    private String activityName;
    /**
     * 活动地点
     */
    @NotNull(message = "活动地点不能为空")
    private String site;
    /**
     * 活动举行时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy年MM月dd")
    @DateTimeFormat(pattern = "yyyy年MM月dd")
    private String holdTime;
    /**
     * 活动介绍
     */
    @NotNull(message = "活动简介不能为空")
    private String introduce;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy年MM月dd")
    @DateTimeFormat(pattern = "yyyy年MM月dd")
    private Date applyTime;
    /**
     * 参赛人员
     */
    @NotNull(message = "参赛人员不能为空")
    private String participants;
    /**
     * 比赛成果
     */
    private String competitionResults;
    /**
     * 队名
     */
    private String groupName;
    /**
     * 会长是否批准
     */
    private Integer permission;
    @NotNull(message = "活动类型不能为空")
    private Integer activityTypeId;
    private Integer clubId;

}