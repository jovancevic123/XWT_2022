package com.xml.xmlbackendzh1.model.zh1;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@XmlTransient
@XmlSeeAlso({FizickoLice.class, PravnoLice.class})
public abstract class Lice{

    protected Adresa adresa;

    protected Kontakt kontakt;

    public Adresa getAdresa() {
        return adresa;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }

    public Kontakt getKontakt() {
        return kontakt;
    }

    public void setKontakt(Kontakt kontakt) {
        this.kontakt = kontakt;
    }
}