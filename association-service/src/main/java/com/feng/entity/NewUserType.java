package com.feng.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 */
@Data
public class NewUserType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章类型
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 文章类型
     */
    @NotBlank(message = "社团类型不能为空")
    private String type;


    @Override
    public String toString() {
        return "newUserType{" +
        "id=" + id +
        ", type=" + type +
        "}";
    }
}
