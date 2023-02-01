package com.xml.xmlbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchResultDto {
    private String brojPrijave;
    private String datumPodnosenja;
    private String podnosilacEmail;
    private String naslovDela;
    private String brojResenja;
}
