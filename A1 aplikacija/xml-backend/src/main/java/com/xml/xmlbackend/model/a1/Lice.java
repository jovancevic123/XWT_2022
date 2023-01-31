package com.xml.xmlbackend.model.a1;

import javax.xml.bind.annotation.*;


@XmlTransient
public abstract class Lice{

    protected Kontakt kontakt;

    public Kontakt getKontakt() {
        return kontakt;
    }

    public void setKontakt(Kontakt kontakt) {
        this.kontakt = kontakt;
    }
}
