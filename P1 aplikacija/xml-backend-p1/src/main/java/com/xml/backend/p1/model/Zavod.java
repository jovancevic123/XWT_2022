package com.xml.backend.p1.model;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "naziv", "adresa" })
public class Zavod {

    @XmlElement(required = true)
    private String naziv;

    @XmlElement(required = true)
    private Adresa adresa;
}
