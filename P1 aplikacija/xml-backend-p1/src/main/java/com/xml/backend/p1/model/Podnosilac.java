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

    public Lice getLice() {
        return lice;
    }

    public void setLice(Lice lice) {
        this.lice = lice;
    }

    public boolean isPronalazac() {
        return pronalazac;
    }

    public void setPronalazac(boolean pronalazac) {
        this.pronalazac = pronalazac;
    }

    public String getDrzavljanstvo() {
        return drzavljanstvo;
    }

    public void setDrzavljanstvo(String drzavljanstvo) {
        this.drzavljanstvo = drzavljanstvo;
    }
}
