package com.xml.backend.p1.model;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name="TPravnoLice", propOrder={"adresa", "kontakt", "poslovnoIme"})
public class PravnoLice extends Lice{

    private String poslovnoIme;

    @Override
    @XmlElement(name="adresa", required = true)
    public Adresa getAdresa() {
        return super.adresa;
    }
    @Override
    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }

    @Override
    @XmlElement(name="kontakt", required = true)
    public Kontakt getKontakt() {
        return super.kontakt;
    }
    @Override
    public void setKontakt(Kontakt kontakt) {
        this.kontakt = kontakt;
    }

    @XmlElement(name="poslovno_ime", required = true)
    public String getPoslovnoIme() {
        return poslovnoIme;
    }

    public void setPoslovnoIme(String poslovnoIme) {
        this.poslovnoIme = poslovnoIme;
    }

    public PravnoLice(Adresa adresa, Kontakt kontakt, String poslovnoIme){
        super(adresa, kontakt);
        this.poslovnoIme = poslovnoIme;
    }

    public PravnoLice(){}

}
