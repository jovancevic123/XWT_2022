package com.xml.xmlbackend.model.a1;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "adresa", "pseudonim"
})
public class Autor extends FizickoLice{
    @XmlElement(name="adresa", required=true)
    private Adresa adresa;

    @XmlElement(name="autor", required=false)
    private String pseudonim;
}
