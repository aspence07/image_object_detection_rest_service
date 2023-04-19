package com.example.imgobjectdetectionrestservice;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.imgobjectdetectionrestservice.db.Image;
import com.example.imgobjectdetectionrestservice.ro.IngestImageRequest;
import com.example.imgobjectdetectionrestservice.ro.IngestImageResponse;
import com.example.imgobjectdetectionrestservice.service.ImageService;

@RestController
@RequestMapping()
public class ImageController {

	@Autowired
	ImageService imageService;

	@GetMapping("/images")
	public List<Image> getAllImages(@RequestParam(required = false) String objects) {
		if (objects != null && !objects.equals("")) {
			List<String> tags = Arrays.asList(objects.split(","));
			return imageService.findImagesWithTags(tags);
		}
		else {
			return imageService.getAllImages();
		}
	}

	@GetMapping("/images/{imageId}")
	public Image findImageById(@PathVariable Long imageId) {
		return imageService.findImageById(imageId);
	}

	@PostMapping("/images") 
	public IngestImageResponse ingestImage(@RequestBody IngestImageRequest ingestImageRequest) throws IOException {
		return imageService.ingestImage(ingestImageRequest);
	}

}