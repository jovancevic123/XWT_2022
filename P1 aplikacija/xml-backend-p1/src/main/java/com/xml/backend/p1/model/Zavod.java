package com.xml.backend.p1.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@Getter
@Setter
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "naziv", "adresa" })
public class Zavod {

    @XmlElement(required = true)
    private String naziv;

    @XmlElement(required = true)
    private Adresa adresa;

    public Zavod(){
        this.naziv = "Zavod za intelektualnu svojinu";
        this.adresa = new Adresa("Kneginje Ljubice", 5, 11000, "Beograd", "Republika Srbija");
    }
}
