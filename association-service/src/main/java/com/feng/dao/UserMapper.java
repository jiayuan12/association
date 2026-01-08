package com.feng.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.feng.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 */

public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT grade, COUNT(*) as count FROM user WHERE grade IS NOT NULL AND grade != '' GROUP BY grade ORDER BY grade")
    List<Map<String, Object>> getGradeStatistics();

    @Select("SELECT class_name, COUNT(*) as count FROM user WHERE class_name IS NOT NULL AND class_name != '' GROUP BY class_name ORDER BY class_name")
    List<Map<String, Object>> getClassStatistics();
}
