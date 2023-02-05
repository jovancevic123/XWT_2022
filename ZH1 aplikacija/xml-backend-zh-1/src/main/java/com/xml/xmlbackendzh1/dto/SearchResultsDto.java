package com.xml.xmlbackendzh1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchResultsDto {
    private String brojPrijaveZiga;
    private String nazivPodnosioca;
    private String brojResenja;

    public SearchResultsDto(String brojPrijaveZiga, String nazivPodnosioca){
        this.brojPrijaveZiga = brojPrijaveZiga;
        this.nazivPodnosioca = nazivPodnosioca;
        this.brojResenja = "";
    }
}