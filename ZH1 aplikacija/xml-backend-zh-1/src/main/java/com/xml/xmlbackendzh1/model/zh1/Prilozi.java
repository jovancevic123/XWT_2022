package com.xml.xmlbackendzh1.model.zh1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "primerakZnaka", "spisakRobeIUsluga", "punomocje", "generalnoPpunomocje", "punomocjeNaknadnoDostavljeno", "opstiAkt", "dokazOPravuPrvenstva", "dokazOUplatiTakse"
})
public class Prilozi {
    @XmlElement(name="primerak_znaka", required=true)
    private Prilog primerakZnaka;

    @XmlElement(name="spisak_robe_i_usluga", required=true)
    private Prilog spisakRobeIUsluga;

    @XmlElement(name="punomocje", required=true)
    private Prilog punomocje;

    @XmlElement(name="generalno_punomocje", required=true)
    private Prilog generalnoPpunomocje;

    @XmlElement(name="punomocje_naknadno_dostavljeno", required=true)
    private Prilog punomocjeNaknadnoDostavljeno;

    @XmlElement(name="opsti_akt", required=true)
    private Prilog opstiAkt;

    @XmlElement(name="dokaz_o_pravu_prvenstva", required=true)
    private Prilog dokazOPravuPrvenstva;

    @XmlElement(name="dokaz_o_uplati_takse", required=true)
    private Prilog dokazOUplatiTakse;

    public Prilog getPrimerakZnaka() {
        return primerakZnaka;
    }

    public void setPrimerakZnaka(Prilog primerakZnaka) {
        this.primerakZnaka = primerakZnaka;
    }

    public Prilog getSpisakRobeIUsluga() {
        return spisakRobeIUsluga;
    }

    public void setSpisakRobeIUsluga(Prilog spisakRobeIUsluga) {
        this.spisakRobeIUsluga = spisakRobeIUsluga;
    }

    public Prilog getPunomocje() {
        return punomocje;
    }

    public void setPunomocje(Prilog punomocje) {
        this.punomocje = punomocje;
    }

    public Prilog getGeneralnoPpunomocje() {
        return generalnoPpunomocje;
    }

    public void setGeneralnoPpunomocje(Prilog generalnoPpunomocje) {
        this.generalnoPpunomocje = generalnoPpunomocje;
    }

    public Prilog getPunomocjeNaknadnoDostavljeno() {
        return punomocjeNaknadnoDostavljeno;
    }

    public void setPunomocjeNaknadnoDostavljeno(Prilog punomocjeNaknadnoDostavljeno) {
        this.punomocjeNaknadnoDostavljeno = punomocjeNaknadnoDostavljeno;
    }

    public Prilog getOpstiAkt() {
        return opstiAkt;
    }

    public void setOpstiAkt(Prilog opstiAkt) {
        this.opstiAkt = opstiAkt;
    }

    public Prilog getDokazOPravuPrvenstva() {
        return dokazOPravuPrvenstva;
    }

    public void setDokazOPravuPrvenstva(Prilog dokazOPravuPrvenstva) {
        this.dokazOPravuPrvenstva = dokazOPravuPrvenstva;
    }

    public Prilog getDokazOUplatiTakse() {
        return dokazOUplatiTakse;
    }

    public void setDokazOUplatiTakse(Prilog dokazOUplatiTakse) {
        this.dokazOUplatiTakse = dokazOUplatiTakse;
    }


}
