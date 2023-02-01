package com.xml.xmlbackendzh1.model.zh1;

import org.apache.catalina.Manager;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "naziv", "zavod", "podnosilac", "punomocnik", "zig", "placeneTakse", "prilozi"
})
@XmlRootElement(name = "zahtev")
public class Zahtev {
    @XmlElement(name="naziv", required = true)
    private String naziv;

    @XmlElement(name="zavod", required=true)
    private Zavod zavod;

    @XmlElement(name="podnosilac", required = true)
    private Lice podnosilac;

    @XmlElement(name="punomocnik", required = true)
    private Lice punomocnik;

    @XmlElement(name = "zig", required = true)
    private Zig zig;

    @XmlElementWrapper(name="placene_takse", required=true)
    @XmlElement(name="taksa", required=true)
    private List<Taksa> placeneTakse = new ArrayList<Taksa>();

    @XmlElement(name="prilozi", required=true)
    private Prilozi prilozi = new Prilozi();

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Zavod getZavod() {
        return zavod;
    }

    public void setZavod(Zavod zavod) {
        this.zavod = zavod;
    }

    public Zig getZig() {
        return zig;
    }

    public void setZig(Zig zig) {
        this.zig = zig;
    }

    public List<Taksa> getPlaceneTakse() {
        return placeneTakse;
    }

    public void setPlaceneTakse(List<Taksa> placeneTakse) {
        this.placeneTakse = placeneTakse;
    }

    public Prilozi getPrilozi() {
        return prilozi;
    }

    public void setPrilozi(Prilozi prilozi) {
        this.prilozi = prilozi;
    }

    public Lice getPodnosilac() {
        return podnosilac;
    }

    public void setPodnosilac(Lice podnosilac) {
        this.podnosilac = podnosilac;
    }

    public Lice getPunomocnik() {
        return punomocnik;
    }

    public void setPunomocnik(Lice punomocnik) {
        this.punomocnik = punomocnik;
    }

    @Override
    public String toString() {
        return "Zahtev{" +
                "naziv='" + naziv + '\'' +
                ", zavod=" + zavod +
                ", podnosilac=" + podnosilac +
                ", punomocnik=" + punomocnik +
                ", zig=" + zig +
                ", placeneTakse=" + placeneTakse +
                ", prilozi=" + prilozi +
                '}';
    }


}

