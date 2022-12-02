package com.xml.xmlbackend.model.a1;


import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name="TPravnoLice", propOrder={"kontakt", "poslovnoIme", "sediste"})
public class PravnoLice extends Lice{

    private String poslovnoIme;

    private String sediste;

    @Override
    @XmlElement(required = true)
    public Kontakt getKontakt() {
        return super.kontakt;
    }
    @Override
    public void setKontakt(Kontakt kontakt) {
        this.kontakt = kontakt;
    }

    @XmlElement(required = true, name = "poslovno_ime")
    public String getPoslovnoIme() {
        return poslovnoIme;
    }

    public void setPoslovnoIme(String poslovnoIme) {
        this.poslovnoIme = poslovnoIme;
    }

    @XmlElement(required = true)
    public String getSediste() {
        return sediste;
    }

    public void setSediste(String sediste) {
        this.sediste = sediste;
    }

}
