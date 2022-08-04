package com.codename1.demos.netflixclone.service;

import com.codename1.demos.netflixclone.dto.ContentCollectionDTO;
import com.codename1.demos.netflixclone.entities.Content;
import com.codename1.demos.netflixclone.entities.ContentCollection;
import com.codename1.demos.netflixclone.entities.ContentCollectionRepository;
import com.codename1.demos.netflixclone.entities.ContentRepository;
import com.codename1.demos.netflixclone.entities.Media;
import com.codename1.demos.netflixclone.entities.MediaRepository;
import com.codename1.demos.netflixclone.entities.VideoQuality;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class VideoService {
    private final ContentRepository contentRepository;
    private final ContentCollectionRepository contentCollectionRepository;
    private final MediaRepository mediaRepository;
    private final ResourceLoader resourceLoader;

    private byte[] getResourceAsBytes(String res) throws IOException {
        final Resource fileResource = resourceLoader.getResource("classpath:" + res);
        @Cleanup InputStream is = fileResource.getInputStream();
        return IOUtils.toByteArray(is);
    }

    @PostConstruct
    public void initDB() throws IOException {
        if(contentRepository.count() == 0) {
            byte[] heroImage = getResourceAsBytes("images/hero-background.jpg");
            Media strangerThingsHeroImage = new Media(null, "hero-background.jpg", "image/jpeg", Instant.now(),
                    heroImage.length, heroImage, null, VideoQuality.NONE);
            List<Media> thumbs = new ArrayList<>();
            for (int iter = 1; iter < 9; iter++) {
                byte[] showIcon = getResourceAsBytes("images/thumb" + iter + ".jpg");
                Media thumb = new Media(null, "thumb.jpg", "image/jpeg", Instant.now(),
                        showIcon.length, showIcon, null, VideoQuality.NONE);
                thumbs.add(thumb);
            }
            byte[] showLogo = getResourceAsBytes("images/show-logo.png");
            Media strangerThingsLogoImage = new Media(null, "show-logo.png", "image/png", Instant.now(),
                    showLogo.length, showLogo, null, VideoQuality.NONE);

            Media lowQualityVideo = new Media(null, "low-quality-video.mp4", "video/mp4",
                    Instant.now(), -1, null,
                    "https://file-examples.com/wp-content/uploads/2017/04/file_example_MP4_480_1_5MG.mp4",
                    VideoQuality.LOW);

            Media mediumQualityVideo = new Media(null, "medium-quality-video.mp4", "video/mp4",
                    Instant.now(), -1, null,
                    "https://file-examples.com/wp-content/uploads/2017/04/file_example_MP4_640_3MG.mp4",
                    VideoQuality.MEDIUM);

            Media highQualityVideo = new Media(null, "high-quality-video.mp4", "video/mp4",
                    Instant.now(), -1, null,
                    "https://file-examples.com/wp-content/uploads/2017/04/file_example_MP4_1280_10MG.mp4",
                    VideoQuality.HIGH);

            Set<Media> videos = new HashSet<>();
            videos.add(lowQualityVideo);
            videos.add(mediumQualityVideo);
            videos.add(highQualityVideo);

            List<Media> allMedia = new ArrayList<>(thumbs);
            allMedia.addAll(Arrays.asList(strangerThingsHeroImage, strangerThingsLogoImage, lowQualityVideo,
                    mediumQualityVideo, highQualityVideo));
            mediaRepository.saveAll(allMedia);


            List<Content> allContent = new ArrayList<>();
            Content lead = new Content(null,
                    "Stranger Things",
                    "Stranger things description",
                    strangerThingsHeroImage,
                    null,
                    strangerThingsLogoImage,
                    videos);
            allContent.add(lead);

            for (int iter = 0; iter < thumbs.size(); iter++) {
                allContent.add(new Content(
                        null,
                        "Show " + iter,
                        "Lorem ipsum",
                        strangerThingsHeroImage,
                        thumbs.get(iter),
                        null,
                        videos));
            }

            Set<Content> popular = new HashSet<>(allContent.subList(0, 4));
            Set<Content> myList = new HashSet<>(allContent.subList(4, 8));
            Set<Content> recommended = new HashSet<>(allContent.subList(2, 6));

            contentRepository.saveAll(allContent);

            ContentCollection contentCollection = new ContentCollection(null, lead, popular, myList, recommended);
            contentCollectionRepository.save(contentCollection);
        }
    }

    public ContentCollectionDTO getContent() {
        return contentCollectionRepository.findAll().get(0).toDTO();
    }
}

