package com.spacenews.demo.service;

import com.spacenews.demo.DTO.SourceDTO;
import com.spacenews.demo.Response.SourceResponse;

public interface SourceService {

    SourceResponse getSources();
    SourceDTO addSource(SourceDTO sourceDTO);
    SourceResponse updateSource(String source);
    SourceResponse deleteSource(String source);

}
