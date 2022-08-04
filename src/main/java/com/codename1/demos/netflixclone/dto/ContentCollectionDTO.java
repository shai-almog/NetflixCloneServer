package com.codename1.demos.netflixclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentCollectionDTO {
    private ContentDTO lead;
    private List<ContentDTO> popular;
    private List<ContentDTO> myList;
    private List<ContentDTO> recommended;
}
