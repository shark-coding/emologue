package com.project.emologue.external;

import com.project.emologue.model.emotion.Dictionary.DictionaryEmotionResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class DictionaryEmotionClient {

    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${python.dictionary-api-url}")
    private String pythonApiUrl;

    public DictionaryEmotionResult analyze(String content) {
        System.out.println("📢 pythonApiUrl = " + pythonApiUrl);
        Map<String, String> request = new HashMap<>();
        request.put("content", content);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<DictionaryEmotionResult> response = restTemplate.exchange(
                pythonApiUrl,
                HttpMethod.POST,
                httpEntity,
                DictionaryEmotionResult.class
        );

        return response.getBody();
    }
}
