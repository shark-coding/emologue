package com.project.emologue.external;

import com.project.emologue.model.emotion.AIPredicted.AIPredictedEmotionResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class PredictorEmotionClient {
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${python.predictor-api-url}")
    private String pythonApiUrl;

    public AIPredictedEmotionResult predictor(String content) {
        System.out.println("📢 pythonApiUrl = " + pythonApiUrl);
        Map<String, String> request = new HashMap<>();
        request.put("content", content);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<AIPredictedEmotionResult> response = restTemplate.exchange(
                pythonApiUrl,
                HttpMethod.POST,
                httpEntity,
                AIPredictedEmotionResult.class
        );

        return response.getBody();
    }
}
