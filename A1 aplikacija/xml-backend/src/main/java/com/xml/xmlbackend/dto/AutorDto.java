package com.xml.xmlbackend.dto;

import com.xml.xmlbackend.model.a1.Kontakt;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AutorDto {
    private KontaktDto kontakt;
    private AdresaDto adresa;
    private String ime;
    private String prezime;
    private String drzavljanstvo;
    private String pseudonim;
}
