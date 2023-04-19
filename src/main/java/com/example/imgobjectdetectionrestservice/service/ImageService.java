package com.example.imgobjectdetectionrestservice.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.imgobjectdetectionrestservice.db.Image;
import com.example.imgobjectdetectionrestservice.db.ImageRepository;
import com.example.imgobjectdetectionrestservice.imagga.ImaggaTagsResponse;
import com.example.imgobjectdetectionrestservice.imagga.Tag;
import com.example.imgobjectdetectionrestservice.ro.IngestImageRequest;
import com.example.imgobjectdetectionrestservice.ro.IngestImageResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ImageService {
    
    @Autowired
    ImageRepository imageRepository;

    public List<Image> findImagesWithTags(List<String> tags) {
        Set<Image> imageSet = new HashSet<>();
        for (String tag : tags) {
            Set<Image> imagesWithTag = new HashSet<>(imageRepository.findByTagsContaining(tag));
            imageSet.addAll(imagesWithTag);
        }
        return new LinkedList<Image>(imageSet);
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    public Image findImageById(Long id) {
        try {
            return imageRepository.findById(id).get();
        }
        catch (NoSuchElementException e) {
            return null;
        }
    }

    public IngestImageResponse ingestImage(IngestImageRequest ingestImageRequest) throws IOException {

        boolean isGetTags = ingestImageRequest.getIsObjectDetectionEnabled();

        String userProvidedInputLabel = ingestImageRequest.getImageLabel();

        Image image = new Image();
        image.setImageUrl(ingestImageRequest.getImageUrl());
        if (isGetTags) {
            List<String> tags = getTags(ingestImageRequest.getImageUrl());
            //TODO use JsonComponent annotation
            image.setTags(new ObjectMapper().writeValueAsString(tags));
            if (userProvidedInputLabel == null || userProvidedInputLabel.isBlank()) {
                if (!tags.isEmpty())
                    image.setImageLabel(tags.get(0));
                else
                    image.setImageLabel(new Timestamp(System.currentTimeMillis()).toString());
            }
            else {
                image.setImageLabel(userProvidedInputLabel);
            }
        }
        else {
            if (userProvidedInputLabel == null || userProvidedInputLabel.isBlank())
                image.setImageLabel(new Timestamp(System.currentTimeMillis()).toString());
            else
                image.setImageLabel(userProvidedInputLabel);
        }
        
        image = imageRepository.save(image);

		IngestImageResponse response = new IngestImageResponse();
        response.setImageId(image.getId());
        response.setImageLabel(image.getImageLabel());
        response.setImageUrl(image.getImageUrl());
        // TODO more elegant json deserialization
        if (image.getTags() != null && !image.getTags().isEmpty())
            response.setTags(new ObjectMapper().readValue(image.getTags(), new TypeReference<List<String>>() {}));

		return response;
	}

    private List<String> getTags(String imageUrl) {
        // TODO do this more elegantly
		String baseUrl = "https://api.imagga.com/v2/tags";
		String requestString = "?" + "image_url=" + imageUrl;
		String url = baseUrl + requestString;
		
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders httpHeaders = new HttpHeaders();
        // TODO put in config
		httpHeaders.set(
			"Authorization", 
		"Basic YWNjXzI2NGNkZjFmZTEwNWVmNDo4NmEyYmY2NTI5ZDQ4OTQ1MDRkYTY3MmZiYWI1OTNhMQ=="
		);

		HttpEntity<String> entity = new HttpEntity<String>(null, httpHeaders);

		ImaggaTagsResponse imaggaTagsResponse = restTemplate.exchange(
			url,
			HttpMethod.GET,
			entity,
			ImaggaTagsResponse.class
		).getBody();

        List<Tag> tags = imaggaTagsResponse.getResult().getTags();
        List<String> tagStrings = tags.stream().map(t->t.getTag().getEn()).collect(Collectors.toList());

        return tagStrings;
    }

}
