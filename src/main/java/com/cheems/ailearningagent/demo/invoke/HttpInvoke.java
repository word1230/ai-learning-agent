package com.cheems.ailearningagent.demo.invoke;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper; // 需要引入 Jackson 库处理 JSON

/**
 * http 调用
 */
public class HttpInvoke {

    // --- 配置部分 ---
    private static final String API_KEY = TestApiKey.API_KEY; // 请替换为您的实际 API Key
    private static final String MODEL_NAME = "qwen-plus"; // 请确认并替换为您的实际模型名称
    private static final String ENDPOINT = "https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation"; // 通义千问文本生成的 DashScope API 端点 [[10]]

    public static void main(String[] args) {
        try {
            String prompt = "你好，通义千问！"; // 您要发送给模型的提示词
            String response = callQwenModel(prompt);
            System.out.println("模型响应:");
            System.out.println(response);

        } catch (Exception e) {
            System.err.println("调用模型时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 调用阿里云百炼平台的通义千问模型
     * @param prompt 发送给模型的文本提示
     * @return 模型的文本回复
     * @throws IOException 网络IO异常
     * @throws InterruptedException 线程中断异常
     */
    public static String callQwenModel(String prompt) throws IOException, InterruptedException {
        // 构建请求体 (JSON)
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", MODEL_NAME);

        Map<String, Object> input = new HashMap<>();
        input.put("prompt", prompt);
        requestBody.put("input", input);

        // 可以根据需要添加 parameters，例如控制输出长度、温度等
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("max_tokens", 1500); // 示例参数
        requestBody.put("parameters", parameters);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(requestBody);

        // 构建 HTTP 请求
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ENDPOINT))
                .timeout(Duration.ofMinutes(2)) // 设置超时时间
                .header("Authorization", "Bearer " + API_KEY) // 使用 API Key 鉴权
                .header("Content-Type", "application/json") // 指定内容类型
                .header("X-DashScope-SSE", "enable") // 启用SSE流式输出，如果需要流式处理可以保留
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody, StandardCharsets.UTF_8)) // 发送POST请求体
                .build();

        // 创建 HTTP 客户端并发送请求
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 处理响应
        int statusCode = response.statusCode();
        String responseBody = response.body();

        if (statusCode == 200) {
            // 解析成功的响应
            try {
                Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
                // 根据实际返回的 JSON 结构提取文本内容
                // 注意：如果启用了SSE，返回格式会不同，需要相应处理流式数据
                Map<String, Object> output = (Map<String, Object>) responseMap.get("output");
                if (output != null) {
                    String text = (String) output.get("text");
                    if (text != null) {
                        return text;
                    } else {
                        System.err.println("响应中未找到 'text' 字段。完整响应: " + responseBody);
                        return responseBody; // 或抛出异常
                    }
                } else {
                    System.err.println("响应中未找到 'output' 字段。完整响应: " + responseBody);
                    return responseBody; // 或抛出异常
                }
            } catch (Exception e) {
                System.err.println("解析响应JSON时出错: " + e.getMessage() + ", 响应内容: " + responseBody);
                return responseBody; // 或抛出异常
            }
        } else {
            // 处理错误响应
            System.err.println("API 调用失败，HTTP状态码: " + statusCode);
            System.err.println("响应体: " + responseBody);
            // 可以根据错误码和响应体内容抛出特定异常
            throw new RuntimeException("API调用失败，状态码: " + statusCode + ", 响应: " + responseBody);
        }
    }
}