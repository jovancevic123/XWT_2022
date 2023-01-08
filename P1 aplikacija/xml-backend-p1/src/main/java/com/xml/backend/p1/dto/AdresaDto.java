package com.xml.backend.p1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdresaDto {

    private String ulica;
    private int broj;
    private int postanskiBroj;
    private String mesto;
    private String drzava;
}
