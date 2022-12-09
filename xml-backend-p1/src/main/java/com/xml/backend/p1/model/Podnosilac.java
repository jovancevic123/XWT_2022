package com.xml.backend.p1.model;


import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "pronalazac", "drzavljanstvo", "lice"
})
public class Podnosilac {

    @XmlElement(required = true)
    private Lice lice;

    @XmlAttribute(name="pronalazac", required = true)
    private boolean pronalazac;

    @XmlElement(name = "drzavljanstvo", required=true)
    private String drzavljanstvo;



}
