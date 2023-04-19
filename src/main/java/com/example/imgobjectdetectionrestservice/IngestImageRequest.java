package com.example.imgobjectdetectionrestservice;

import lombok.Data;

@Data
public class IngestImageRequest {
    
    private String imageUrl;

    private String imageLabel;

    private Boolean isObjectDetectionEnabled;
}
