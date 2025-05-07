package com.spacenews.demo.serviceImpl;

import com.spacenews.demo.DTO.PostDTO;
import com.spacenews.demo.Response.NewsResponse;
import com.spacenews.demo.model.Post;
import com.spacenews.demo.repositories.PostRepository;
import com.spacenews.demo.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public NewsResponse getNews(Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "publishedAt"));
        Page<Post> pagePosts = postRepository.findAll(pageable);
        List<Post> posts = pagePosts.getContent();
        List<PostDTO> postDTOs = posts.stream()
                .map((post -> modelMapper.map(post, PostDTO.class))).toList();

        NewsResponse response = new NewsResponse();
        response.setContent(postDTOs);
        response.setPageNumber(pageNumber);
        response.setPageSize(pageSize);
        response.setTotalElements(pagePosts.getTotalElements());
        response.setTotalPages(pagePosts.getTotalPages());

        return response;
    }
}
