package com.example.newsapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.Map;

@Service
public class NewsService {
    private final RestClient restClient;
    private final String apiKey;
    private final String country;

    public NewsService(
            @Value("${newsapi.api-key:}") String apiKey,
            @Value("${newsapi.country:in}") String country) {
        this.apiKey = apiKey;
        this.country = country;
        this.restClient = RestClient.builder()
            .baseUrl("https://newsapi.org/v2")
            .defaultHeader(HttpHeaders.ACCEPT, "application/json")
            .build();
    }

    public Map<String, Object> getTopHeadlines() {
        if (apiKey == null || apiKey.isBlank() || "YOUR_NEWSAPI_KEY".equals(apiKey)) {
            throw new IllegalStateException("NEWS_API_KEY is missing. Get an API key from NewsAPI.org and set the NEWS_API_KEY environment variable.");
        }

        try {
            return restClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path("/top-headlines")
                    .queryParam("country", country)
                    .build())
                .header("X-Api-Key", apiKey)
                .retrieve()
                .body(Map.class);
        } catch (RestClientResponseException ex) {
            throw new RuntimeException("NewsAPI.org returned HTTP " + ex.getStatusCode(), ex);
        }
    }
}
