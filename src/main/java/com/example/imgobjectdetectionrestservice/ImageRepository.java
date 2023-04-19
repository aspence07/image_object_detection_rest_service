package com.example.imgobjectdetectionrestservice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    
    public List<Image> findByTagsContaining(String tags);

}
