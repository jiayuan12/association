package com.feng.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.feng.entity.Message;

import java.util.List;

public interface MessageMapper {
    void insert(Message message);

    List<Message> getPublishedMessages();

    List<Message> findActivity(Message search);

    void updateStatus(Message message);

    Message selectById(Long id);

    void deleteById(Long id);
}
