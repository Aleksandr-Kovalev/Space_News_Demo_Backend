package com.spacenews.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spacenews.demo.service.RssFeedService;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class RssFeedController {

    private final RssFeedService rssFeedService;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("It works!");
    }

    @GetMapping("/demo")
    public ResponseEntity<List<Map<String, String>>> getNewsDemo(){

        List<Map<String, String>> feedList = rssFeedService.getSyndEntries().stream()
                                        .map(entry -> Map.of(
                                                "title", entry.getTitle(),
                                                "link", entry.getLink(),
                                                "publishingDate", entry.getPublishedDate().toString(),
                                                "description", entry.getDescription() != null ? entry.getDescription().getValue() : ""
                                        ))
                                        .toList();

        if(feedList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(feedList, HttpStatus.OK);
    }

}
