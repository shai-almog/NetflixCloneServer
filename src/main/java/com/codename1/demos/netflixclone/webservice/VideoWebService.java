package com.codename1.demos.netflixclone.webservice;

import com.codename1.demos.netflixclone.dto.ContentCollectionDTO;
import com.codename1.demos.netflixclone.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/video")
@RestController
@RequiredArgsConstructor
public class VideoWebService {
    private final VideoService videoService;

    @GetMapping("/list")
    public ContentCollectionDTO getContent() {
        return videoService.getContent();
    }
}
