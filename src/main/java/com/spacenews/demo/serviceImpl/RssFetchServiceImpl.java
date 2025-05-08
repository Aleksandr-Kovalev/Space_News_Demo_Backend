package com.spacenews.demo.serviceImpl;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.spacenews.demo.model.Post;
import com.spacenews.demo.model.PostType;
import com.spacenews.demo.model.Source;
import com.spacenews.demo.model.SourceType;
import com.spacenews.demo.repositories.PostRepository;
import com.spacenews.demo.repositories.SourceRepository;
import com.spacenews.demo.service.PostService;
import com.spacenews.demo.service.RssFetchService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RssFetchServiceImpl implements RssFetchService {

    private final SourceRepository sourceRepository;
    private final PostRepository postRepository;
    private final PostService postService;

    @Override
    @Scheduled(fixedRate = 900000)
    public void fetchRssFeeds() {

        System.out.println("Fetching rss feeds, total post count before fetching rss feeds is:" + postRepository.count());

        List<Source> sources = sourceRepository.findAll();
        System.out.println("Fetching rss feeds sources counted:" + sources.size());
        for (Source source : sources) {
            try {
                if(source.getType() == SourceType.RSS) {
                    List<Post> newPosts = parseRssFeeds(source);
                    postService.saveAll(newPosts);
                }
            } catch (Exception e) {
                System.err.println("Failed to fetch from source: " + source.getName() + ", id: " + source.getId());
                e.printStackTrace();
            }
        }

        System.out.println("Fetched rss feeds, source size: " + sources.size() + " New Total Post count: " + postRepository.count());

    }

    //Rome Tool Documentation
    //https://rometools.github.io/rome/index.html
    private List<Post> parseRssFeeds(Source source) {

        System.out.println("Parsing rss feeds from source: " + source.getName());
        System.out.println("Parsing rss feeds from source url: " + source.getUrl());
        SyndFeed feed;

        try {
            URL feedUrl = new URL(source.getUrl());
            SyndFeedInput input = new SyndFeedInput();
            feed = input.build(new XmlReader(feedUrl));
            //System.out.println("feed: " + feed.toString());
        } catch (IOException | FeedException e) {
            System.err.println("Malformed URL: " + source.getUrl());
            throw new RuntimeException(e);
        }

        List<Post> newPosts = new ArrayList<>();
        for (SyndEntry entry : feed.getEntries()) {
            Post post = new Post();
            post.setTitle(entry.getTitle());
            post.setLink(entry.getLink());
            post.setContent(entry.getDescription().getValue());
            post.setPublishedAt(entry.getPublishedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            post.setFetchedAt(LocalDateTime.now());
            post.setSource(source);
            post.setType(PostType.RSS);
            post.setImageUrl(parseRSSFeedForImages(entry)); //parse for image url
            newPosts.add(post);
        }

        System.out.println("Parsing rss feeds from source: " + source.getName() + " New Post count: " + newPosts.size());

        return newPosts;

    }

    private String parseRSSFeedForImages(SyndEntry entry) {

        String imageUrl = "";

        // 1. Check enclosures for image
        List<SyndEnclosure> enclosures = entry.getEnclosures();
        for (SyndEnclosure enclosure : enclosures) {
            if (enclosure.getType() != null && enclosure.getType().startsWith("image")) {
                imageUrl = enclosure.getUrl();
                System.out.println("Image found in enclosure: " + imageUrl);
                break;
            }
        }

        // 2. Check description for <img>
        if (imageUrl.isEmpty() && entry.getDescription() != null) {
            imageUrl = extractImageFromHtml(entry.getDescription().getValue());
            if (!imageUrl.isEmpty()) {
                System.out.println("Image found in description: " + imageUrl);
            }
        }

        // 3. Check contents for <img>
        if (imageUrl.isEmpty() && entry.getContents() != null) {
            for (SyndContent content : entry.getContents()) {
                imageUrl = extractImageFromHtml(content.getValue());
                if (!imageUrl.isEmpty()) {
                    System.out.println("Image found in content: " + imageUrl);
                    break;
                }
            }
        }

        // 4. Fallback to default
        if (imageUrl.isEmpty()) {
            imageUrl = "placeholder";  // Replace with your default
            System.out.println("No image found. Using default: " + imageUrl);
        }

        return imageUrl;
    }

    private String extractImageFromHtml(String html) {
        Document doc = Jsoup.parse(html);
        Element img = doc.selectFirst("img");
        return img != null ? img.attr("src") : "";
    }
}
