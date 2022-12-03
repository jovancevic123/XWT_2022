package com.xml.backend.p1.model;


import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name="TFizickoLice", propOrder={"adresa", "kontakt", "ime", "prezime"})
public class FizickoLice extends Lice{

    private String ime;
    private String prezime;

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

    @XmlElement(name="ime", required = true)
    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    @XmlElement(name="prezime", required = true)
    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
}
