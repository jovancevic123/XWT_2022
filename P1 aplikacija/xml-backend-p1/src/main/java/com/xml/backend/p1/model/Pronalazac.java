package com.xml.backend.p1.model;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "navedenUPrijavi", "lice"
})
public class Pronalazac {

    @XmlElement(required = true)
    private Lice lice;

    @XmlAttribute(name = "naveden_u_prijavi", required=true)
    private boolean navedenUPrijavi;
}
