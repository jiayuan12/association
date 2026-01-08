package com.feng.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Equipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 是否需要修复
     */
    private String needRepair;

    /**
     * 使用情况
     */
    private String useStatus;

    private LocalDateTime useTime;

    private String remark;

    private String operationStatus;

    private String isDeleted;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy年MM月dd日")
    @DateTimeFormat(pattern = "yyyy年MM月dd日")
    private String createTime;


    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", needRepair='" + needRepair + '\'' +
                ", useStatus='" + useStatus + '\'' +
                ", useTime='" + useTime + '\'' +
                ", remark='" + remark + '\'' +
                ", operationStatus='" + operationStatus + '\'' +
                ", isDelete='" + isDeleted + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
