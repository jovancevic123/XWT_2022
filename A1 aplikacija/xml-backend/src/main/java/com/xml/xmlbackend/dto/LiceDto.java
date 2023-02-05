package com.xml.xmlbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LiceDto {
    private String ime;
    private String prezime;
    private String drzavljanstvo;
    private AdresaDto adresa;
    private KontaktDto kontakt;

    private String poslovnoIme;
    private String sediste;
}
