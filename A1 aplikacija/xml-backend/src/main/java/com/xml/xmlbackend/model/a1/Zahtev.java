package com.xml.xmlbackend.model.a1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "prijava", "podnosilac", "punomocnik", "autorskoDelo", "prilozi", "zavod"
})
@XmlRootElement(name = "zahtev")
public class Zahtev {
    @XmlElement(required = true)
    private Prijava prijava;

    @XmlElement(required = true)
    private Podnosilac podnosilac;

    @XmlElement(required = false)
    private Lice punomocnik;

    @XmlElement(name = "autorsko_delo",required = true)
    private AutorskoDelo autorskoDelo;

    @XmlElementWrapper(name="prilozi", required=false)
    @XmlElement(name="prilog", required=true)
    private List<Prilog> prilozi = new ArrayList<Prilog>();

    @XmlElement(required=true)
    private Zavod zavod;

    @XmlElement(name="broj_resenja", required = false)
    private String brojResenja;

    public Prijava getPrijava() {
        return prijava;
    }

    public void setPrijava(Prijava prijava) {
        this.prijava = prijava;
    }

    public Podnosilac getPodnosilac() {
        return podnosilac;
    }

    public void setPodnosilac(Podnosilac podnosilac) {
        this.podnosilac = podnosilac;
    }

    public Lice getPunomocnik() {
        return punomocnik;
    }

    public void setPunomocnik(Lice punomocnik) {
        this.punomocnik = punomocnik;
    }

    public AutorskoDelo getAutorskoDelo() {
        return autorskoDelo;
    }

    public void setAutorskoDelo(AutorskoDelo autorskoDelo) {
        this.autorskoDelo = autorskoDelo;
    }

    public List<Prilog> getPrilozi() {
        return prilozi;
    }

    public void setPrilozi(List<Prilog> prilozi) {
        this.prilozi = prilozi;
    }

    public Zavod getZavod() {
        return zavod;
    }

    public void setZavod(Zavod zavod) {
        this.zavod = zavod;
    }

    @Override
    public String toString() {
        return "Zahtev{" +
                "prijava=" + prijava +
                ", podnosilac=" + podnosilac +
                ", punomocnik=" + punomocnik +
                ", autorskoDelo=" + autorskoDelo +
                ", prilozi=" + prilozi +
                ", zavod=" + zavod +
                '}';
    }
}
