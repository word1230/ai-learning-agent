package com.cheems.ailearningagent.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 网页搜索工具
 */
public class WebSearchTools {

    // 替换为您的实际 API Key
    private static final String API_KEY = "w1x1HDhmdv6pqXKbM34x1hk7";
    private static final String BASE_URL = "https://www.searchapi.io/api/v1/search";
    private static final String ENGINE = "baidu";

    /**
     * 调用 SearchApi 的 Baidu 搜索引擎进行搜索。
     *
     * @param query 搜索查询词 (必需)
     * @return API 响应的 JSON 字符串
     * @throws IOException 如果发生 IO 错误
     * @throws InterruptedException 如果操作被中断
     */
    @Tool(description = "Search for information from Baidu Search Engine")
    public static String searchBaidu(@ToolParam(description = "Search query keyword")String query
                                     ) throws IOException, InterruptedException {
        // 构建查询参数
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("engine", ENGINE);
        queryParams.put("q", query); // URL 编码将在构建 URI 时处理
        queryParams.put("api_key", API_KEY);


        // 构建完整的 URL 查询字符串
        StringBuilder queryString = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            if (!first) {
                queryString.append("&");
            }
            // 对键和值进行 URL 编码以处理特殊字符
            queryString.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            queryString.append("=");
            queryString.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
            first = false;
        }

        String fullUrl = BASE_URL + "?" + queryString.toString();

        // 创建 HttpClient 实例
        HttpClient client = HttpClient.newHttpClient();

        // 构建 HttpRequest
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .GET() // 默认就是 GET，可以省略
                .build();

        // 发送请求并获取响应
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 返回响应体 (JSON 字符串)
        return response.body();
    }


}
