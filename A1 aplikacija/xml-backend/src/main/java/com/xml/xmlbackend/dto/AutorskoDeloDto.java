package com.xml.xmlbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AutorskoDeloDto {
    private boolean anonimanAutor;
    private String formaDela;
    private boolean jeDeloPrerade;
    private String nacinKoriscenjaDela;
    private String naslovDela;
    private boolean uRadnomOdnosu;
    private String vrstaDela;
    private List<AutorDto> autoriDela;
}
