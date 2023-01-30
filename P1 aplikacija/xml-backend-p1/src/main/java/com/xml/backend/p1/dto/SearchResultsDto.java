package com.xml.backend.p1.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchResultsDto {
    private String brojPrijave;
    private String nazivPodnosioca;
    private String nazivPatenta;
    private String brojResenja;

    public SearchResultsDto(String brojPrijave, String nazivPodnosioca, String nazivPatenta){
        this.brojPrijave = brojPrijave;
        this.nazivPodnosioca = nazivPodnosioca;
        this.nazivPatenta = nazivPatenta;
        this.brojResenja = "";
    }
}
