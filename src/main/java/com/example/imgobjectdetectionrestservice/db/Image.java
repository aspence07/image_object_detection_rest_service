package com.example.imgobjectdetectionrestservice.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name = "images")
public class Image {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String imageUrl;

    @Column
    private String imageLabel;

    @Column(length = 4096)
    @JsonIgnore
    private String tags;

    @Transient
    @JsonGetter("tags")
    public List<String> getTagList() {
        if (tags == null || tags.isBlank())
            return new ArrayList<String>(0);

        try {
            return Arrays.asList(new ObjectMapper().readValue(tags, String[].class));
        }
        catch (JsonProcessingException e) {
            return null;
        }
    }
    
}
