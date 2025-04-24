package com.spacenews.demo.serviceImpl;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.spacenews.demo.model.Feeds;
import org.springframework.stereotype.Service;
import com.spacenews.demo.service.RssFeedService;

import java.net.URL;
import java.util.List;

@Service
public class RssFeedServiceImpl implements RssFeedService {

    @Override
    public List<SyndEntry> getSyndEntries() {

        try{
            Feeds feeds = new Feeds();
            URL feedUrl = new URL(feeds.getNASA_RSS_URL());
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));
            return feed.getEntries();

        } catch (Exception e){
            throw new RuntimeException("Failed to fetch or parse RSS feed", e);
        }
    }
}
