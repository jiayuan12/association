package com.feng.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.feng.dao.AttendanceMapper;
import com.feng.dao.NewUserMapper;
import com.feng.dao.NewUserTypeMapper;
import com.feng.dao.UserMapper;
import com.feng.entity.Attendance;
import com.feng.entity.NewUser;
import com.feng.entity.NewUserType;
import com.feng.entity.User;
import com.feng.enums.ErrorEnum;
import com.feng.exception.BusinessException;
import com.feng.service.NewUserService;
import com.feng.vo.NewUserInfoVo;
import com.feng.vo.NewUserPageVo;
import com.feng.vo.NewUserVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author rf
 * @since 2019-04-14
 */
@Service
public class NewUserServiceImpl extends ServiceImpl<NewUserMapper, NewUser> implements NewUserService {
    @Autowired
    private AttendanceMapper attendanceMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NewUserMapper newUserMapper;
    @Autowired
    private NewUserTypeMapper newUserTypeMapper;
    @Override
    public NewUserVo getTopN(int n, NewUser search) {
        NewUserType newUserType = null;
        Wrapper<NewUser> newUserWrapper = new EntityWrapper<>();
        if (search.getUserTypeId() != null) {
            newUserWrapper.eq("new_user_type_id", search.getUserTypeId());
            newUserType = newUserTypeMapper.selectById(search.getUserTypeId());
        }
        PageHelper.startPage(1, n);
        List<NewUser> newUserList = newUserMapper.selectList(newUserWrapper);
        return new NewUserVo(newUserType,newUserList);
    }

    /**
     * 分页查询所有新人
     */
    public PageInfo<NewUserInfoVo> getPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<NewUserInfoVo> userList = this.baseMapper.selectAllWithVo();
        return new PageInfo<>(userList);
    }


    @Override
    public NewUserPageVo getPageWithTypeList(int pageNum, int pageSize, Integer newUserTypeId) {
        NewUserType newUserType = null;
        Wrapper<NewUser> newUserWrapper = new EntityWrapper<>();
        if (newUserTypeId != null) {
            newUserWrapper.eq("user_type_id", newUserTypeId);
            newUserType = newUserTypeMapper.selectById(newUserTypeId);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<NewUser> newUserList = newUserMapper.selectList(newUserWrapper);
        return new NewUserPageVo(newUserType,new PageInfo<>(newUserList));
    }

    @Override
    @Cacheable(value = "newUser")
    public NewUser getById(Serializable id) {
        NewUser newUser = newUserMapper.selectById(id);
        if (newUser == null) {
            throw new BusinessException(ErrorEnum.BUSINESS_EXCEPTION.setMsg("新人不存在"));
        }
        return newUser;
    }

    @Override
//    @CachePut(value = "newUser", key = "#newUser.id")
    public NewUser add(NewUser newUser) {
        newUserMapper.insert(newUser);
        return newUser;
    }

    @Override
    @CacheEvict(value = "newUser", key = "#id")
    public boolean deleteById(Serializable id) {
        Assert.notNull(id, "社团id不能为空");
        newUserMapper.deleteById(id);
        return true;
    }

    /**
     * 条件分页查询新人
     */
    public PageInfo<NewUserInfoVo> searchPage(int pageNum, int pageSize, NewUser search) {
        PageHelper.startPage(pageNum, pageSize);
        List<NewUserInfoVo> userList = newUserMapper.searchByCondition(search);
        return new PageInfo<>(userList);
    }

    /**
     * 统计各班级人数分布
     */
    public List<Map<String, Object>> getClassStatistics() {
        return this.baseMapper.getClassStatistics();
    }

    /**
     * 统计各成绩区间人数分布
     */
    public List<Map<String, Object>> getScoreStatistics() {
        return this.baseMapper.getScoreStatistics();
    }

    @Override
    public List<NewUserInfoVo> searchNewUsers(String name, String className, Integer score) {
        NewUserInfoVo searchCondition = new NewUserInfoVo();
        if (name != null && !name.isEmpty()) {
            searchCondition.setName(name);
        }
        if (className != null && !className.isEmpty()) {
            searchCondition.setClassName(className);
        }
        if (score != null) {
            searchCondition.setScore(score);
        }

        // 复用已有的mapper方法
        return newUserMapper.searchByCondition(searchCondition);
    }

    @Override
    @Transactional
    public boolean addToMembers(Long id) {
        NewUser newUser = newUserMapper.getInfoById(id);
        if (newUser != null){
            User user = new User();
            user.setName(newUser.getName());
            if (newUser.getSex().equals("男")){
                user.setSex('男');
            }else if (newUser.getSex().equals("女")){
                user.setSex('女');
            }
            user.setClassName(newUser.getClassName());
            user.setGrade(String.valueOf(LocalDate.now().getYear()).substring(2,4)+"级");
            user.setCallName("成员");
            user.setAccount(newUser.getAccount());
            user.setUserProfile(newUser.getIntroduce());
            userMapper.insert(user);
            newUserMapper.deleteById(id);
            Attendance attendance = new Attendance();
            attendance.setUserId(user.getId());
            attendance.setStatus("unsigned");
            attendance.setAttendanceTime(null);
            attendance.setRemark(null);
            attendanceMapper.insert(attendance);
            return true;
        }

        return false;
    }

    @Override
    public int batchDeleteByCondition(String name, String className, Integer score) {
        // 执行批量删除
        return newUserMapper.batchDelete(name,className, score);
    }


    @Override
//    @CachePut(value = "newUser", key = "#newUser.id")
    public NewUser updateWithId(NewUser newUser) {
        newUserMapper.updateById(newUser);
        return newUser;
    }
}
