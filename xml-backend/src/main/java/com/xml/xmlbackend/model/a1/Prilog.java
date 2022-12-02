package com.xml.xmlbackend.model.a1;


import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "vrstaPriloga", "src"
})
public class Prilog {

    @XmlAttribute(required=true, name = "vrsta_priloga")
    private String vrstaPriloga;

    @XmlAttribute(required=true)
    private String src;
}
