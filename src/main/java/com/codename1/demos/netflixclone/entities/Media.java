package com.codename1.demos.netflixclone.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String mimeType;
    private Instant modified;
    private long size;

    @Lob
    @Column(name = "media", columnDefinition="BLOB")
    private byte[] media;

    private String mediaURL;
    private VideoQuality quality;
}
