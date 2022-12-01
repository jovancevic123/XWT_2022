package com.xml.xmlbackend.model.a1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"poslovnoIme", "sediste"})
public class PravnoLice {

    @XmlElement(required = true, name = "poslovno_ime")
    private String poslovnoIme;

    @XmlElement(required = true)
    private String sediste;
}
