package com.xml.xmlbackend.model.a1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name="TFizickoLice", propOrder={"kontakt", "ime", "prezime", "adresa", "drzavljanstvo"})
public class FizickoLice extends Lice{

    private String ime;

    private String prezime;

    private Adresa adresa;

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
    public Adresa getAdresa() {
        return this.adresa;
    }

    public void setAdresa(Adresa adresa){
        this.adresa = adresa;
    }

    @XmlElement(required = true)
    public String getDrzavljanstvo() {
        return drzavljanstvo;
    }

    public void setDrzavljanstvo(String drzavljanstvo) {
        this.drzavljanstvo = drzavljanstvo;
    }


}
