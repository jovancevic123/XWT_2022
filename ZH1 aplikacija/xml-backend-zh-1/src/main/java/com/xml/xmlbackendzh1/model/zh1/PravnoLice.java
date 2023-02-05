package com.xml.xmlbackendzh1.model.zh1;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@NoArgsConstructor
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name="TPravnoLice", propOrder={"adresa", "kontakt", "poslovnoIme"})
public class PravnoLice extends Lice{

    private String poslovnoIme;

    public PravnoLice(Adresa adresa, Kontakt kontakt, String poslovnoIme){
        super(adresa, kontakt);
        this.poslovnoIme = poslovnoIme;
    }

    @XmlElement(required = true, name = "poslovno_ime")
    public String getPoslovnoIme() {
        return poslovnoIme;
    }

    public void setPoslovnoIme(String poslovnoIme) {
        this.poslovnoIme = poslovnoIme;
    }

    @Override
    @XmlElement(required = true)
    public Adresa getAdresa() {
        return super.adresa;
    }
    @Override
    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }

    @Override
    @XmlElement(required = true)
    public Kontakt getKontakt() {
        return super.kontakt;
    }
    @Override
    public void setKontakt(Kontakt kontakt) {
        this.kontakt = kontakt;
    }
}