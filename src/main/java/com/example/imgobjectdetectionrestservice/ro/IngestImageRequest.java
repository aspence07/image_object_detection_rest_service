package com.example.imgobjectdetectionrestservice.ro;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class IngestImageRequest {
    
    @Schema(example = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/15/Cat_August_2010-4.jpg/1920px-Cat_August_2010-4.jpg")
    private String imageUrl;

    @Schema(example = "Cat")
    private String imageLabel;

    @Schema(example = "true")
    private Boolean isObjectDetectionEnabled;
}
