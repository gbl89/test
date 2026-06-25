package com.gbl.demo.invoke;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

//cURL利用hutool工具去实习调用大模型
public class AliDashScopeTest {
    public static void main(String[] args) {
        // 1. 配置信息
        String url = "https://ws-jchzacgfuk8ukgag.cn-beijing.maas.aliyuncs.com/api/v1/services/aigc/text-generation/generation";
        String apiKey = TestApikey.API_KEY; // 替换成自己的密钥

        // 2. 构造请求JSON体
        JSONObject bodyJson = new JSONObject();
        bodyJson.put("model", "qwen3.7-max");

        // input -> messages
        JSONObject inputObj = new JSONObject();
        inputObj.put("messages", JSONUtil.createArray()
                .put(JSONUtil.createObj()
                        .put("role", "user")
                        .put("content", "你是谁？")
                )
        );
        bodyJson.put("input", inputObj);

        // parameters
        JSONObject paramObj = new JSONObject();
        paramObj.put("enable_thinking", true);
        paramObj.put("result_format", "message");
        bodyJson.put("parameters", paramObj);

        // 3. 发送POST请求
        HttpResponse response = HttpRequest.post(url)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .body(bodyJson.toString()) // 传入JSON字符串
                .timeout(30000) // 超时30秒
                .execute();

        // 4. 打印结果
        System.out.println("响应码：" + response.getStatus());
        System.out.println("响应内容：" + response.body());
    }
}