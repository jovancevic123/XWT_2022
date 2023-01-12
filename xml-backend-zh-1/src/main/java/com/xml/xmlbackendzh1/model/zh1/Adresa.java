package com.xml.xmlbackendzh1.model.zh1;

import com.xml.xmlbackendzh1.dto.AdresaDto;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "ulica", "broj", "postanskiBroj", "mesto", "drzava"
})
public class Adresa {

    @XmlElement(required=true)
    private String ulica;

    @XmlElement(required=true)
    private int broj;

    @XmlElement(required=true, name = "postanski_broj")
    private int postanskiBroj;

    @XmlElement(required=true)
    private String mesto;

    @XmlElement(required=true)
    private String drzava;

    public Adresa(AdresaDto dto){
        this.ulica = dto.getUlica();
        this.broj = dto.getBroj();
        this.postanskiBroj = dto.getPostanskiBroj();
        this.mesto = dto.getMesto();
        this.drzava = dto.getDrzava();
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public int getBroj() {
        return broj;
    }

    public void setBroj(int broj) {
        this.broj = broj;
    }

    public int getPostanskiBroj() {
        return postanskiBroj;
    }

    public void setPostanskiBroj(int postanskiBroj) {
        this.postanskiBroj = postanskiBroj;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    @Override
    public String toString() {
        return "Adresa{" +
                "ulica='" + ulica + '\'' +
                ", broj=" + broj +
                ", postanskiBroj=" + postanskiBroj +
                ", mesto='" + mesto + '\'' +
                ", drzava='" + drzava + '\'' +
                '}';
    }
}