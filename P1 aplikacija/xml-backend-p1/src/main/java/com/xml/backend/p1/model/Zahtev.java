package com.xml.backend.p1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "prijava", "zavod", "pronalazak", "podnosilac", "pronalazac",
        "punomoc", "ranijePrijave", "nacinDostavljanja", "brojResenja", "adresaDostave"
})
@XmlRootElement(name = "zahtev")
public class Zahtev {

    @XmlElement(required = true)
    private Prijava prijava;

    @XmlElement(required = true)
    private Zavod zavod;

    @XmlElement(required = true)
    private Pronalazak pronalazak;

    @XmlElement(required = true)
    private Podnosilac podnosilac;

    @XmlElement(required = true)
    private Pronalazac pronalazac;

    @XmlElement(required = true)
    private Punomoc punomoc;

    @XmlElementWrapper(name="ranije_prijave", required=true)
    @XmlElement(name="ranija_prijava")
    private List<RanijaPrijava> ranijePrijave = new ArrayList<RanijaPrijava>();

    @XmlElement(name="nacin_dostavljanja", required = true)
    private String nacinDostavljanja;

    @XmlElement(name="broj_resenja", required = true)
    private String brojResenja;

    @XmlElement(name="adresa_dostave", required = false)
    private Adresa adresaDostave;
}
