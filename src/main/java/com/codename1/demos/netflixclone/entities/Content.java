package com.codename1.demos.netflixclone.entities;

import com.codename1.demos.netflixclone.dto.ContentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String description;

    @OneToOne
    private Media heroImage;

    @OneToOne
    private Media icon;

    @OneToOne
    private Media logo;

    @ManyToMany
    private Set<Media> videos;

    public ContentDTO getDTO() {
        Map<VideoQuality, String> qualityUrls = videos.stream().
                collect(Collectors.
                        toMap(Media::getQuality,
                                Media::getMediaURL));
        return new ContentDTO(id.toString(),
                name, description,
                getMedia(heroImage), getMedia(icon),
                getMedia(logo), qualityUrls);
    }

    private byte[] getMedia(Media m) {
        return m == null ? null : m.getMedia();
    }
}
