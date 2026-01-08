package com.feng.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.feng.dao.AttendanceMapper;
import com.feng.dao.UserMapper;
import com.feng.entity.Attendance;
import com.feng.entity.User;
import com.feng.enums.ErrorEnum;
import com.feng.exception.BusinessException;
import com.feng.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 */
@Service
public class UserServiceImpl implements UserService {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AttendanceMapper attendanceMapper;

    @Override
    public PageInfo<User> getUserPage(int num, int size, User search) {
        PageHelper.startPage(num, size);
        Wrapper<User> userWrapper = new EntityWrapper();
        List<User> userList = userMapper.selectList(userWrapper);
        return new PageInfo<>(userList);
    }

    @Override
    public User login(User user) {
        List<User> userList = getByAccount(user.getAccount());
        if (userList.isEmpty()) {
            throw new BusinessException(ErrorEnum.USER_NAME_ERROR);
        }
        User loginUser = userList.get(0);
        if (loginUser.getLogin()!=1){
            throw new BusinessException(ErrorEnum.UNAUTHORIZED);
        }
        //我要如何设置setLastLoginTime为当前时间
        loginUser.setLastLoginTime(String.valueOf(System.currentTimeMillis()));
        if (!loginUser.getPassword().equals(user.getPassword())) {
            throw new BusinessException(ErrorEnum.USER_PASSWORD_ERROR);
        }
        return loginUser;
    }

    @Override
    public List<User> getByAccount(String account) {
        EntityWrapper<User> userEntityWrapper = new EntityWrapper<>();
        userEntityWrapper.eq("account", account);
        return userMapper.selectList(userEntityWrapper);
    }

    @Override
    public User getOneByAccount(String account) {
        List<User> userList = getByAccount(account);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        } else {
          return   userList.get(0);
        }
    }

    @Override
    public User add(User user) {
        userMapper.insert(user);
        //当添加完之后还要添加到每日签到表里面
        Attendance attendance = new Attendance();
        attendance.setUserId(user.getId());
        attendance.setStatus("unsigned");
        attendance.setAttendanceTime(null);
        attendance.setRemark(null);
        attendanceMapper.insert(attendance);
        return user;
    }

    @Override
    @CachePut(value = "user", key = "#user.id")
    public User updateById(User user) {
        userMapper.updateById(user);
        return user;
    }

    @Override
    @Cacheable(value = "user", key = "#id")
    public User getById(Serializable id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(ErrorEnum.BUSINESS_EXCEPTION.setMsg("该用户不存在"));
        }
        return user;
    }

    @Override
    @CacheEvict(value = "user", key = "#id")
    public void DeleteById(Serializable id) {
        Attendance attendance = attendanceMapper.selectByUserId((Integer) id);
        if (attendance != null) {
            attendanceMapper.deleteById(attendance.getId());
        }
        userMapper.deleteById(id);
    }

    @Override
    public User register(User user, String rePassword) {
//        Assert.notNull(rePassword,"确认密码不能为空！");
        List<User> userList = getByAccount(user.getAccount());
        if (!userList.isEmpty()) {
            User user1 = userList.get(0);
            if (user1.getIsActive()) {
                throw new BusinessException(ErrorEnum.BUSINESS_EXCEPTION.setMsg("QQ号已经激活，请直接登录"));
            } else {
                throw new BusinessException(ErrorEnum.BUSINESS_EXCEPTION.setMsg("请尽快激活QQ号"));
            }
        }
        if (user.getPassword().equals(rePassword)) {
            throw new BusinessException(ErrorEnum.USER_RE_PASSWORD_ERROR);
        }
        userMapper.insert(user);

        return user;
    }

    @Override
    public PageInfo<User> searchUsers(int pageNum, int pageSize, User search) {
        PageHelper.startPage(pageNum, pageSize);
        // 构建查询条件
        Wrapper<User> wrapper = new EntityWrapper<>();
        if (search.getName() != null && !search.getName().isEmpty()) {
            wrapper.like("name", search.getName());
        }
        if (search.getGrade() != null && !search.getGrade().isEmpty()) {
            wrapper.eq("grade", search.getGrade());
        }
        if (search.getClassName() != null && !search.getClassName().isEmpty()) {
            wrapper.eq("class_name", search.getClassName());
        }
        if (search.getCallName() != null && !search.getCallName().isEmpty()) {
            wrapper.eq("call_name", search.getCallName());
        }
        List<User> userList = userMapper.selectList(wrapper);
        return new PageInfo<>(userList);
    }

    @Override
    public List<Map<String, Object>> getGradeStatistics() {
        return userMapper.getGradeStatistics();
    }

    @Override
    public List<Map<String, Object>> getClassStatistics() {
        return userMapper.getClassStatistics();
    }
}
