package com.spacenews.demo.service;

import com.spacenews.demo.Response.NewsResponse;

public interface NewsService {

    NewsResponse getNews(Integer pageNumber, Integer pageSize);

}
