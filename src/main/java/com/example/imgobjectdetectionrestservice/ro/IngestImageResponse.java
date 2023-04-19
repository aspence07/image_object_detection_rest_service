package com.example.imgobjectdetectionrestservice.ro;

import java.util.List;
import lombok.Data;

@Data
public class IngestImageResponse {
    
    private String imageUrl;

    private String imageLabel;

    private Long imageId;

    private List<String> tags;
}
