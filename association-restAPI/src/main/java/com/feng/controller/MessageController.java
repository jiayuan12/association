package com.feng.controller;

import com.feng.entity.Message;
import com.feng.entity.ResponseResult;
import com.feng.service.MessageService;
import com.feng.util.ResponseResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 提交留言
     */
    @PostMapping
    public ResponseResult addMessage(@RequestBody Message message, HttpServletRequest request) {
        // 获取客户端IP
        String ip = getClientIpAddress(request);
        message.setIp(ip);
        message.setStatus(0);
        messageService.saveMessage(message);
        return ResponseResultUtil.renderSuccess("留言提交成功");

    }

    /**
     * 获取已发布的留言
     */
    @GetMapping("/published")
    public ResponseResult getPublishedMessages() {
        List<Message> messages = messageService.getPublishedMessages();
        return ResponseResultUtil.renderSuccess(messages);
    }

    /**
     * 获取客户端IP地址
     */
    private String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}