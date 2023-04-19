package com.example.imgobjectdetectionrestservice.imagga;

import lombok.Data;

@Data
public class Tag {
    private Long confidence;
    private SubTag tag;
}
