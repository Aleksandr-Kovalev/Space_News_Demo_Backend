package com.spacenews.demo.DTO;

import com.spacenews.demo.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SourceDTO {
    private Long id;
    private String name;
    private String url;
    private String homepageUrl;
    private LocalDateTime createdAt;
}
