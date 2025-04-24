package com.spacenews.demo.model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Feeds {

    private static final String NASA_RSS_URL = "https://www.nasa.gov/news-release/feed/";
    private String RSS_URL;

    public String getNASA_RSS_URL() {
        return NASA_RSS_URL;
    }

}
