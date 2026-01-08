package com.feng.service;

import com.feng.entity.Message;
import com.github.pagehelper.PageInfo;

import javax.validation.Valid;
import java.util.List;

public interface MessageService {
    void saveMessage(Message message);

    List<Message> getPublishedMessages();

    PageInfo<Message> getPage(int pageNum, int pageSize, Message search);

    void togglePublish(Long id);

    void deleteById(Long id);
}
