package com.feng.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/ai")
public class AIChatController {

    // 定义机器人协会知识库
    private static final String CLUB_INFO =
            "机器人协会是一个专注于机器人技术研究和应用的学生组织。" +
                    "协会主要活动包括：机器人设计与制作、编程竞赛、技术培训、项目实践等。" +
                    "协会定期举办技术分享会、机器人比赛、创新项目展示等活动。" +
                    "协会欢迎对机器人技术有兴趣的同学加入，无论是否有基础，我们都提供学习机会。" +
                    "成立时间比较久远，请暂且下次了解，现有成员50多人，指导老师3名。" +
                    "协会获得过多项省级和国家级机器人竞赛奖项。";

    // 常见问题和预设答案
    private static final Map<String, String> FAQ_MAP = new HashMap<>();

    static {
        // 初始化常见问题
        FAQ_MAP.put("加入", "任何人都可以加入机器人协会，无论是否有基础。我们提供学习机会和培训课程，帮助新成员快速成长。请联系负责人或查看官网了解加入流程。想加入的话，可以在新人报名的地方去报名，到时候会进行考核，考核通过后就能正式进入协会了");
        FAQ_MAP.put("活动", "协会主要活动包括：机器人设计与制作、编程竞赛、技术培训、项目实践、技术分享会、机器人比赛、创新项目展示等。");
        FAQ_MAP.put("最新活动", "请认真看协会的最新通知，最新活动会在最新通知中提示");
        FAQ_MAP.put("成立时间", "成立时间比较久远，请暂且下次了解");
        FAQ_MAP.put("成员", "协会现有成员50多人，指导老师3名。");
        FAQ_MAP.put("人", "协会现有成员50多人，指导老师3名。");
        FAQ_MAP.put("获奖", "协会获得过多项省级和国家级机器人竞赛奖项。");
        FAQ_MAP.put("联系", "您可以通过协会官方QQ号1061866985联系协会。");
        FAQ_MAP.put("培训", "协会定期举办技术培训，包括编程、嵌入式等方向的培训课程。");
        FAQ_MAP.put("帅哥", "协会帅哥很多，但最帅的莫过于小源前会长了");
        FAQ_MAP.put("美女", "协会美女很多，太多了，都很漂亮");
    }

    @RequestMapping(value = "/chat", produces = "application/json;charset=utf-8")
    public ResponseEntity<Map<String, Object>> chat(String message, String sessionId) {
        if (message == null || message.trim().isEmpty()) {
            Map<String, Object> response = createErrorResponse("请提供有效的问题");
            return ResponseEntity.ok(response);
        }

        String responseContent = generateResponse(message);

        Map<String, Object> response = createSuccessResponse(responseContent);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/chat", produces = "application/json;charset=utf-8")
    public ResponseEntity<Map<String, Object>> chatPost(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        String sessionId = request.get("sessionId");

        if (message == null || message.trim().isEmpty()) {
            Map<String, Object> response = createErrorResponse("请提供有效的问题");
            return ResponseEntity.ok(response);
        }

        String responseContent = generateResponse(message);

        Map<String, Object> response = createSuccessResponse(responseContent);
        return ResponseEntity.ok(response);
    }

    // 生成响应内容
    private String generateResponse(String userMessage) {
        String message = userMessage.toLowerCase();

        // 检查是否包含常见问题关键词
        for (Map.Entry<String, String> entry : FAQ_MAP.entrySet()) {
            if (message.contains(entry.getKey())) {
                return entry.getValue();
            }
        }

        // 检查一些特殊问题
        if (isGreeting(message)) {
            return "您好！我是机器人协会的AI助手小圆，很高兴为您服务！您可以问我关于协会的任何问题。";
        }

        if (isThankYou(message)) {
            return "不客气！如果您还有其他关于机器人协会的问题，随时可以问我。";
        }

        if (isAboutAI(message)) {
            return "我是机器人协会的专属AI助手小圆，专门为您解答关于机器人协会的各种问题。我可以告诉您协会的部门、活动、加入方式等信息。";
        }

        // 如果问题与机器人协会无关，礼貌提醒
        if (!isRelatedToClub(message)) {
            return "您好！我是机器人协会的专属AI助手小圆，我只能回答关于机器人协会的问题。关于其他问题，我可能无法提供帮助。您可以问我协会的成员、活动、加入方式等相关问题。";
        }

        // 默认响应
        return "感谢您的提问！关于这个问题，您可以了解更多关于机器人协会的信息：" +
                "机器人协会是一个专注于机器人技术研究和应用的学生组织，欢迎对机器人技术有兴趣的同学加入。" +
                "如果您有更具体的问题，欢迎继续提问！";
    }

    // 检查是否为问候语
    private boolean isGreeting(String message) {
        String[] greetings = {"你好", "您好", "hello", "hi", "你好啊", "您好啊"};
        for (String greeting : greetings) {
            if (message.contains(greeting)) {
                return true;
            }
        }
        return false;
    }

    // 检查是否为感谢语
    private boolean isThankYou(String message) {
        String[] thanks = {"谢谢", "感谢", "谢谢了", "感谢你", "thank", "thanks"};
        for (String thank : thanks) {
            if (message.contains(thank)) {
                return true;
            }
        }
        return false;
    }

    // 检查是否在询问AI本身
    private boolean isAboutAI(String message) {
        String[] aboutAI = {"你是谁", "你是什么", "介绍下自己", "你的功能", "你能做什么", "你是"};
        for (String about : aboutAI) {
            if (message.contains(about)) {
                return true;
            }
        }
        return false;
    }

    // 检查问题是否与机器人协会相关
    private boolean isRelatedToClub(String message) {
        String[] clubRelated = {"机器人", "协会", "社团", "加入", "活动", "比赛", "培训", "成员", "老师", "项目", "编程", "嵌入式", "小圆"};
        for (String related : clubRelated) {
            if (message.contains(related)) {
                return true;
            }
        }
        return false;
    }

    // 创建成功响应
    private Map<String, Object> createSuccessResponse(String content) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "success");
        response.put("data", Map.of("reply", content));
        return response;
    }

    // 创建错误响应
    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 500);
        response.put("message", message);
        response.put("data", null);
        return response;
    }
}