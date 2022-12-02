package com.xml.xmlbackend.model.a1;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name="TFizickoLice", propOrder={"kontakt", "ime", "prezime", "drzavljanstvo"})
public class FizickoLice extends Lice{

    private String ime;

    private String prezime;

    private String drzavljanstvo;

    @Override
    @XmlElement(required = true)
    public Kontakt getKontakt() {
        return super.kontakt;
    }
    @Override
    public void setKontakt(Kontakt kontakt) {
        this.kontakt = kontakt;
    }

    @XmlElement(required = true)
    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    @XmlElement(required = true)
    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    @XmlElement(required = true)
    public String getDrzavljanstvo() {
        return drzavljanstvo;
    }

    public void setDrzavljanstvo(String drzavljanstvo) {
        this.drzavljanstvo = drzavljanstvo;
    }
}
