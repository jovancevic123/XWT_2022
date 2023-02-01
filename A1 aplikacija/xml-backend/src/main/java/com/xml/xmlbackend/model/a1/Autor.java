package com.xml.xmlbackend.model.a1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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


    public Autor(Lice podnosilac){
        if(podnosilac instanceof FizickoLice) {
            FizickoLice pod = (FizickoLice) podnosilac;
            this.setIme(pod.getIme());
            this.setPrezime(pod.getPrezime());
            this.setDrzavljanstvo(pod.getDrzavljanstvo());
            this.setAdresa(pod.getAdresa());
            this.setKontakt(pod.getKontakt());
        }
        else{
            PravnoLice pod = (PravnoLice) podnosilac;
            this.setIme(pod.getPoslovnoIme());
            this.setKontakt(pod.getKontakt());
            Adresa adresa = new Adresa();
            adresa.setMesto(pod.getSediste());
            adresa.setDrzava("");
            adresa.setBroj(0);
            adresa.setPostanskiBroj(11000);
            adresa.setUlica("");
            this.setAdresa(adresa);
        }
    }

}
