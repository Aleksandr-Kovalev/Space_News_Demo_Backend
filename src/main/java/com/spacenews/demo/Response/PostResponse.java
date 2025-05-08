package com.spacenews.demo.Response;

import com.spacenews.demo.model.PostType;
import com.spacenews.demo.model.Source;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    private long id;
    private Source source;
    private PostType type;
    private String title;
    private LocalDateTime publishingDate;
    private LocalDateTime fetchedDate;
    private String link;
    private String content;
    private String imageUrl;
    private String category;
    private String locObject;
}
