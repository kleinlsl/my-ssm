package com.tujia.myssm.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author: songlinl
 * @create: 2025/2/24 16:49
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class ApiController extends BaseController {

    @GetMapping("/data")
    public String data() {
        return "success";
    }

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/proxy")
    public ResponseEntity<String> proxyRequest(@RequestBody Map<String, Object> requestData) {
        // 提取请求参数
        String url = (String) requestData.get("url");
        String method = (String) requestData.get("method");
        Map<String, String> params = (Map<String, String>) requestData.get("params");
        Map<String, String> headers = (Map<String, String>) requestData.get("headers");
        Object body = requestData.get("data");

        // 构造请求头
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::add);

        // 构造请求实体
        HttpEntity<Object> requestEntity = new HttpEntity<>(body, httpHeaders);

        // 发送请求
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    buildUrlWithParams(url, params),
                    HttpMethod.valueOf(method),
                    requestEntity,
                    String.class
            );
            return response;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    private String buildUrlWithParams(String url, Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return url;
        }
        StringBuilder urlBuilder = new StringBuilder(url);
        urlBuilder.append("?");
        params.forEach((key, value) -> urlBuilder.append(key).append("=").append(value).append("&"));
        return urlBuilder.substring(0, urlBuilder.length() - 1); // 去掉最后一个 "&"
    }
}
