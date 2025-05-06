package com.spacenews.demo.controller;

import com.spacenews.demo.DTO.SourceDTO;
import com.spacenews.demo.Response.CustomResponse;
import com.spacenews.demo.Response.SourceResponse;
import com.spacenews.demo.service.SourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class SourceController {

    private final SourceService sourceService;

    @GetMapping("/sources")
    public ResponseEntity<SourceResponse> getSource() {
        SourceResponse sourceResponse = sourceService.getSources();
        return new ResponseEntity<>(sourceResponse, HttpStatus.OK);
    }

    @PostMapping("/sources")
    public ResponseEntity<SourceDTO> addSource(@RequestBody SourceDTO sourceDTO) {
        SourceDTO savedSourceDTO = sourceService.addSource(sourceDTO);
        return new ResponseEntity<>(savedSourceDTO, HttpStatus.CREATED);
    }

    @PutMapping("/sources/{id}")
    public ResponseEntity<SourceDTO> updateSource(@RequestBody SourceDTO sourceDTO,
                                                  @PathVariable Long id) {
        SourceDTO savedSourceDTO = sourceService.updateSource(sourceDTO, sourceDTO.getId());
        return new ResponseEntity<>(savedSourceDTO, HttpStatus.OK);
    }

    @DeleteMapping("/sources/{id}")
    public ResponseEntity<CustomResponse<SourceDTO>> deleteSource(@PathVariable Long id) {
        SourceDTO deletedSource = sourceService.deleteSource(id);
        CustomResponse<SourceDTO> response = new CustomResponse<>("Source deleted successfully", deletedSource);
        return ResponseEntity.ok(response);
    }


}
