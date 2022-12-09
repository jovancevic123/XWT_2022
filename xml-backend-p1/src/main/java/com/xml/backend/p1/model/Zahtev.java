package com.xml.backend.p1.model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "prijava", "zavod", "pronalazak", "podnosilac", "pronalazac",
        "punomoc", "ranijePrijave", "nacinDostavljanja", "adresaDostave"
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

    @XmlElement(name="adresa_dostave", required = true)
    private Adresa adresaDostave;

    public Prijava getPrijava() {
        return prijava;
    }

    public void setPrijava(Prijava prijava) {
        this.prijava = prijava;
    }

    public Zavod getZavod() {
        return zavod;
    }

    public void setZavod(Zavod zavod) {
        this.zavod = zavod;
    }

    public Pronalazak getPronalazak() {
        return pronalazak;
    }

    public void setPronalazak(Pronalazak pronalazak) {
        this.pronalazak = pronalazak;
    }

    public Podnosilac getPodnosilac() {
        return podnosilac;
    }

    public void setPodnosilac(Podnosilac podnosilac) {
        this.podnosilac = podnosilac;
    }

    public Pronalazac getPronalazac() {
        return pronalazac;
    }

    public void setPronalazac(Pronalazac pronalazac) {
        this.pronalazac = pronalazac;
    }

    public Punomoc getPunomoc() {
        return punomoc;
    }

    public void setPunomoc(Punomoc punomoc) {
        this.punomoc = punomoc;
    }

    public List<RanijaPrijava> getRanijePrijave() {
        return ranijePrijave;
    }

    public void setRanijePrijave(List<RanijaPrijava> ranijePrijave) {
        this.ranijePrijave = ranijePrijave;
    }

    public String getNacinDostavljanja() {
        return nacinDostavljanja;
    }

    public void setNacinDostavljanja(String nacinDostavljanja) {
        this.nacinDostavljanja = nacinDostavljanja;
    }

    public Adresa getAdresaDostave() {
        return adresaDostave;
    }

    public void setAdresaDostave(Adresa adresaDostave) {
        this.adresaDostave = adresaDostave;
    }
}
