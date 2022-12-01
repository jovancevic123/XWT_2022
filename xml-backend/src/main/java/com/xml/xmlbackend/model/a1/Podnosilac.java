package com.xml.xmlbackend.model.a1;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "podnosilacJeAutor"
})
public class Podnosilac extends Lice{

    @XmlAttribute(required=true, name = "podnosilac_je_autor")
    private String podnosilacJeAutor;

}
