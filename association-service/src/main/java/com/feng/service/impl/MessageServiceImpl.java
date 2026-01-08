package com.feng.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.feng.dao.MessageMapper;
import com.feng.dto.ActivityTypeDto;
import com.feng.entity.Message;
import com.feng.service.MessageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 留言服务实现类
 * </p>
 *
 * @author robotics club
 * @since 2024
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public void saveMessage(Message message) {
        messageMapper.insert(message);
    }

    @Override
    public List<Message> getPublishedMessages() {
        return messageMapper.getPublishedMessages();
    }

    @Override
    public PageInfo<Message> getPage(int pageNum, int pageSize, Message search) {
        PageHelper.startPage(pageNum, pageSize);
        List<Message> messageList = messageMapper.findActivity(search);
        return new PageInfo<>(messageList);
    }

    @Override
    public void togglePublish(Long id) {
        Message message = messageMapper.selectById(id);
        if (message != null) {
            if (message.getStatus() == 0){
                message.setStatus(1);
            }else if (message.getStatus() == 1){
                message.setStatus(0);
            }
        }
        messageMapper.updateStatus(message);
    }

    @Override
    public void deleteById(Long id) {
        messageMapper.deleteById(id);
    }
}