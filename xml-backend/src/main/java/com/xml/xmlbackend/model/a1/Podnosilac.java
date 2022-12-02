package com.xml.xmlbackend.model.a1;


import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "podnosilacJeAutor", "lice"
})
public class Podnosilac{

    @XmlAttribute(required=false, name = "podnosilac_je_autor")
    private String podnosilacJeAutor;

    @XmlElement(required = true)
    private Lice lice;

    public String getPodnosilacJeAutor() {
        return podnosilacJeAutor;
    }

    public void setPodnosilacJeAutor(String podnosilacJeAutor) {
        this.podnosilacJeAutor = podnosilacJeAutor;
    }

    public Lice getLice() {
        return lice;
    }

    public void setLice(Lice lice) {
        this.lice = lice;
    }
}
