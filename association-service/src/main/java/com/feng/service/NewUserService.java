package com.feng.service;

import com.baomidou.mybatisplus.service.IService;
import com.feng.entity.NewUser;
import com.feng.vo.NewUserInfoVo;
import com.feng.vo.NewUserPageVo;
import com.feng.vo.NewUserVo;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 */
public interface NewUserService extends IService<NewUser> {
    NewUserVo getTopN(int n, NewUser search);

    PageInfo<NewUserInfoVo> getPage(int pageNum, int pageSize);

    NewUserPageVo getPageWithTypeList(int pageNum, int pageSize, Integer clubTypeId);

    NewUser getById(Serializable id);

    NewUser add(NewUser club);

    NewUser updateWithId(NewUser club);

    boolean deleteById(Serializable id);

    PageInfo<NewUserInfoVo> searchPage(int pageNum, int pageSize, NewUser search);

    List<Map<String, Object>> getClassStatistics();

    List<Map<String, Object>> getScoreStatistics();

    List<NewUserInfoVo> searchNewUsers(String name, String className, Integer score);

    boolean addToMembers(Long id);

    int batchDeleteByCondition(String name, String className, Integer score);
}
