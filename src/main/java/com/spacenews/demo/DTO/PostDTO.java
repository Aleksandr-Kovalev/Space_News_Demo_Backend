package com.spacenews.demo.DTO;

import com.spacenews.demo.model.PostType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private long id;
    private SourceDTO source;
    private PostType type;
    private String title;
    private LocalDateTime publishedAt;
    private String link;
    private String content;
    private String imageUrl;
    private String category;
    private String locObject;
}
