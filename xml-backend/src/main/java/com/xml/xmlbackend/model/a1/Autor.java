package com.xml.xmlbackend.model.a1;

import javax.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "adresa", "pseudonim", "godinaSmrti"
})
public class Autor extends FizickoLice{
    @XmlElement(required=true)
    private Adresa adresa;

    @XmlElement(required=false)
    private String pseudonim;

    @XmlElement(required=false)
    private int godinaSmrti;

    public Adresa getAdresa() {
        return adresa;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }

    public String getPseudonim() {
        return pseudonim;
    }

    public void setPseudonim(String pseudonim) {
        this.pseudonim = pseudonim;
    }

    public int getGodinaSmrti() {
        return godinaSmrti;
    }

    public void setGodinaSmrti(int godinaSmrti) {
        this.godinaSmrti = godinaSmrti;
    }


}
