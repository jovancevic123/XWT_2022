package com.xml.xmlbackendzh1.model.zh1;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@NoArgsConstructor
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name="TFizickoLice", propOrder={"adresa", "kontakt", "ime", "prezime"})
public class FizickoLice extends Lice{

    private String ime;

    private String prezime;

    public FizickoLice(Adresa adresa, Kontakt kontakt, String ime, String prezime){
        super(adresa, kontakt);
        this.ime = ime;
        this.prezime = prezime;
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
