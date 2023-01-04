package com.xml.backend.p1.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "nazivPronalaskaSRB", "nazivPronalaskaENG" })
public class Pronalazak {

    @XmlElement(name="naziv_pronalaska_srb", required = true)
    private String nazivPronalaskaSRB;

    @XmlElement(name="naziv_pronalaska_eng", required = true)
    private String nazivPronalaskaENG;

    public String getNazivPronalaskaSRB() {
        return nazivPronalaskaSRB;
    }

    public void setNazivPronalaskaSRB(String nazivPronalaskaSRB) {
        this.nazivPronalaskaSRB = nazivPronalaskaSRB;
    }

    public String getNazivPronalaskaENG() {
        return nazivPronalaskaENG;
    }

    public void setNazivPronalaskaENG(String nazivPronalaskaENG) {
        this.nazivPronalaskaENG = nazivPronalaskaENG;
    }
}
