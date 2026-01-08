package com.feng.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author rf
 * @since 2019-04-27
 */
@Setter
@Getter
public class FileType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 社团类型
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 社团类型
     */
    private String type;


    @Override
    public String toString() {
        return "FileType{" +
        "id=" + id +
        ", type=" + type +
        "}";
    }
}
