package com.spacenews.demo.serviceImpl;

import com.spacenews.demo.DTO.SourceDTO;
import com.spacenews.demo.Response.SourceResponse;
import com.spacenews.demo.exceptions.APIException;
import com.spacenews.demo.exceptions.ResourceNotFoundException;
import com.spacenews.demo.model.Source;
import com.spacenews.demo.repositories.SourceRepository;
import com.spacenews.demo.service.SourceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SourceServiceImpl implements SourceService {

    private final SourceRepository sourceRepository;
    private final ModelMapper modelMapper;

    @Override
    public SourceResponse getSources() {

        SourceResponse sourceResponse = new SourceResponse();
        List<Source> sources = sourceRepository.findAll();

        if (sources.isEmpty()) {
            throw new APIException("No sources found");
        }

        List<SourceDTO> sourceDTOs = sources.stream()
                .map(source -> modelMapper.map(source, SourceDTO.class))
                .collect(Collectors.toList());
        sourceResponse.setSources(sourceDTOs);

        return sourceResponse;
    }

    @Override
    public SourceDTO addSource(SourceDTO sourceDTO) {

        Source source = modelMapper.map(sourceDTO, Source.class);
        Source sourceFromDB = sourceRepository.findByUrl(sourceDTO.getUrl());

        if (sourceFromDB != null) {
            throw new APIException("Source already exists");
        }

        source.setCreatedAt(LocalDateTime.now());
        Source savedSource = sourceRepository.save(source);

        return modelMapper.map(savedSource, SourceDTO.class);
    }

    @Override
    public SourceDTO updateSource(SourceDTO sourceDTO, Long id) {

        sourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Source", "id", id));

        Source newSource = modelMapper.map(sourceDTO, Source.class);
        newSource.setId(id);
        Source updatedSource = sourceRepository.save(newSource);

        return modelMapper.map(updatedSource, SourceDTO.class);
    }

    @Override
    public SourceDTO deleteSource(Long id) {
        Source source = sourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Source", "id", id));
        sourceRepository.delete(source);
        return modelMapper.map(source, SourceDTO.class);
    }
}
