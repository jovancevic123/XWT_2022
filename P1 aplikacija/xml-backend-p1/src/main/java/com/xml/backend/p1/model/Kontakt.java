package com.xml.backend.p1.model;

import com.xml.backend.p1.dto.KontaktDto;

import javax.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "telefon", "email", "fax"
})
public class Kontakt {

    @XmlElement(name="telefon", required=true)
    private String telefon;

    @XmlElement(name="email", required=true)
    private String email;

    @XmlElement(name="fax")
    private String fax;

    public Kontakt(KontaktDto dto){
        this.telefon = dto.getBroj();
        this.email = dto.getEmail();
        this.fax = dto.getFax();
    }
}
