package com.codename1.demos.netflixclone.dto;

import com.codename1.demos.netflixclone.entities.VideoQuality;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentDTO {
    private String id;
    private String name;
    private String description;
    private byte[] heroImage;
    private byte[] icon;
    private byte[] logo;
    private Map<VideoQuality, String> videoUrls;
}
