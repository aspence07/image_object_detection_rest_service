package com.example.imgobjectdetectionrestservice.imagga;

import org.springframework.boot.convert.DataSizeUnit;

import java.util.List;

import lombok.Data;

@Data
public class Result {
    private List<Tag> tags;
}