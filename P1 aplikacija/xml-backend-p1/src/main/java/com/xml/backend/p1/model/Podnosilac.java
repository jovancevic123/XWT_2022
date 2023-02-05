package com.xml.backend.p1.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
