package com.feng.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.feng.entity.NewUser;
import com.feng.vo.NewUserInfoVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rf
 * @since 2019-04-14
 */
public interface NewUserMapper extends BaseMapper<NewUser> {
    List<NewUserInfoVo> findNewUser(NewUser newUser);

    List<NewUserInfoVo> selectAllWithVo();

    List<NewUserInfoVo> searchByCondition(NewUser search);


    /**
     * 统计各班级人数分布
     */
    @Select("SELECT class_name as className, COUNT(*) as count " +
            "FROM new_user " +
            "GROUP BY class_name " +
            "ORDER BY count DESC")
    List<Map<String, Object>> getClassStatistics();


    /**
     * 统计各成绩区间人数分布
     */
    @Select({
            "SELECT",
            "  CASE",
            "    WHEN score < 60 THEN '60分以下'",
            "    WHEN score >= 60 AND score < 70 THEN '60-70分'",
            "    WHEN score >= 70 AND score < 80 THEN '70-80分'",
            "    WHEN score >= 80 AND score < 90 THEN '80-90分'",
            "    WHEN score >= 90 AND score <= 100 THEN '90-100分'",
            "    ELSE '其他'",
            "  END as scoreRange,",  // 修改这里
            "  COUNT(*) as count",
            "FROM new_user",
            "WHERE score IS NOT NULL",
            "GROUP BY",
            "  CASE",
            "    WHEN score < 60 THEN '60分以下'",
            "    WHEN score >= 60 AND score < 70 THEN '60-70分'",
            "    WHEN score >= 70 AND score < 80 THEN '70-80分'",
            "    WHEN score >= 80 AND score < 90 THEN '80-90分'",
            "    WHEN score >= 90 AND score <= 100 THEN '90-100分'",
            "    ELSE '其他'",
            "  END"
    })
    List<Map<String, Object>> getScoreStatistics();

    NewUser getInfoById(Long id);

    int batchDelete(String name, String className, Integer score);
}
