package com.example.newsapp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class NewsController {
    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/")
    public Map<String, String> home() {
        return Map.of(
            "application", "Spring Boot News Application",
            "headlines_endpoint", "/api/news",
            "health_endpoint", "/api/health",
            "note", "Set NEWS_API_KEY to a valid NewsAPI.org API key."
        );
    }

    @GetMapping("/api/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }

    @GetMapping("/api/news")
    public ResponseEntity<?> getHeadlines() {
        try {
            return ResponseEntity.ok(newsService.getTopHeadlines());
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("error", ex.getMessage()));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(Map.of("error", "Unable to retrieve news from NewsAPI.org. Check the API key, internet connection, and NewsAPI quota."));
        }
    }
}
