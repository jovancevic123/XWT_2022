package com.xml.backend.p1.model;


import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "ulica", "broj", "postanskiBroj", "mesto", "drzava"
})
public class Adresa {

    @XmlElement(required=true)
    private String ulica;

    @XmlElement(required=true)
    private int broj;

    @XmlElement(name = "postanski_broj", required=true)
    private int postanskiBroj;

    @XmlElement(required=true)
    private String mesto;

    @XmlElement(required=true)
    private String drzava;
}
