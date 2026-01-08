package com.feng.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.feng.dto.ActivityFileDto;
import com.feng.dto.ActivityTypeDto;
import com.feng.entity.Activity;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 */
public interface ActivityMapper extends BaseMapper<Activity> {
    List<ActivityTypeDto> findActivity(Activity activity);

    ActivityFileDto getInfoById(Integer id);

    Integer add(Activity activity);

    /**
     * 查询活动地点统计信息
     */
    @Select("SELECT site as site, COUNT(*) as count " +
            "FROM activity " +
            "WHERE site IS NOT NULL AND site != '' " +
            "GROUP BY site " +
            "ORDER BY count DESC")
    List<Map<String, Object>> selectSiteStatistics();

    /**
     * 查询比赛成果统计信息
     */
    @Select("SELECT competition_results as result, COUNT(*) as count " +
            "FROM activity " +
            "WHERE competition_results IS NOT NULL AND competition_results != '无' " +
            "GROUP BY competition_results " +
            "ORDER BY count DESC")
    List<Map<String, Object>> selectResultStatistics();
}
