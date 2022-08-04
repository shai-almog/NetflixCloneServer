package com.codename1.demos.netflixclone.entities;

import com.codename1.demos.netflixclone.dto.ContentCollectionDTO;
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
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private Content lead;

    @ManyToMany
    private Set<Content> popular;

    @ManyToMany
    private Set<Content> myList;

    @ManyToMany
    private Set<Content> recommended;

    public ContentCollectionDTO toDTO() {
        return new ContentCollectionDTO(lead.getDTO(), asDTO(popular), asDTO(myList), asDTO(recommended));
    }

    private List<ContentDTO> asDTO(Set<Content> cnt) {
        return cnt.stream().
                map(Content::getDTO).
                collect(Collectors.toList());
    }
}
