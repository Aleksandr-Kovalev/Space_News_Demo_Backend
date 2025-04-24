package com.spacenews.demo.service;

import com.rometools.rome.feed.synd.SyndEntry;

import java.util.List;

public interface RssFeedService {

    public List<SyndEntry> getSyndEntries();
}
