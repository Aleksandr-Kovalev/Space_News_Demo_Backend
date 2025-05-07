package com.spacenews.demo.controller;

import com.spacenews.demo.Response.NewsResponse;
import com.spacenews.demo.config.AppConstants;
import com.spacenews.demo.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/feed")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/all")
    public ResponseEntity<NewsResponse> getNews(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize
            ) {
        NewsResponse newsResponse = newsService.getNews(pageNumber, pageSize);
        return ResponseEntity.ok(newsResponse);
    }

}
