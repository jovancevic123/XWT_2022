package com.xml.backend.p1.model;


import com.xml.backend.p1.dto.AdresaDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "ulica", "broj", "postanskiBroj", "mesto", "drzava"
})
public class Adresa {

    @XmlElement(required=true)
    private String ulica;

    @XmlElement(required=true)
    private int broj;

    @XmlElement(name = "postanski_broj", required=true)
    private int postanskiBroj;

    @XmlElement(required=true)
    private String mesto;

    @XmlElement(required=true)
    private String drzava;

    public Adresa(AdresaDto dto){
        this.ulica = dto.getUlica();
        this.broj = dto.getBroj();
        this.postanskiBroj = dto.getPostanskiBroj();
        this.mesto = dto.getMesto();
        this.drzava = dto.getDrzava();
    }
}
