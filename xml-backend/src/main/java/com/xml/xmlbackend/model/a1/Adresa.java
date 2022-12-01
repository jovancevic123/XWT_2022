package com.xml.xmlbackend.model.a1;

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

    @XmlElement(required=true, name = "postanski_broj")
    private int postanskiBroj;

    @XmlElement(required=true)
    private int mesto;

    @XmlElement(required=true)
    private int drzava;
}
