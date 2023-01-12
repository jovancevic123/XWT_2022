package com.xml.xmlbackendzh1.model.zh1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name="TPravnoLice", propOrder={"kontakt", "adresa", "poslovnoIme"})
public class PravnoLice extends Lice{

    private String poslovnoIme;

    public PravnoLice(Adresa adresa, Kontakt kontakt, String poslovnoIme){
        super(kontakt, adresa);
        this.poslovnoIme = poslovnoIme;
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

    @Override
    @XmlElement(required = true)
    public Adresa getAdresa() {
        return super.adresa;
    }
    @Override
    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }

    @XmlElement(required = true, name = "poslovno_ime")
    public String getPoslovnoIme() {
        return poslovnoIme;
    }

    public void setPoslovnoIme(String poslovnoIme) {
        this.poslovnoIme = poslovnoIme;
    }

}