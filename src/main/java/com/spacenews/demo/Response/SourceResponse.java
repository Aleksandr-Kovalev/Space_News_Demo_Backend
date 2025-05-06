package com.spacenews.demo.Response;

import com.spacenews.demo.DTO.SourceDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SourceResponse {
    private List<SourceDTO> sources;
}
