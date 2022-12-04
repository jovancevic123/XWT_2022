package com.xml.xmlbackendzh1.model.zh1;
import javax.xml.bind.annotation.*;


@XmlTransient
@XmlSeeAlso({FizickoLice.class, PravnoLice.class})
public abstract class Lice{

    protected Kontakt kontakt;

    protected Adresa adresa;

    public Kontakt getKontakt() {
        return kontakt;
    }

    public void setKontakt(Kontakt kontakt) {
        this.kontakt = kontakt;
    }

    public Adresa getAdresa() {
        return adresa;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }
}
