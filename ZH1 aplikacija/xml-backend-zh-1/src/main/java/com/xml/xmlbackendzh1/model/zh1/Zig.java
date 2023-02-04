package com.xml.xmlbackendzh1.model.zh1;
import com.xml.xmlbackendzh1.model.common.LocalDateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "tipZiga", "znak", "brojeviKlasaRobe", "zatrazenoPravo", "brojPrijaveZiga",
        "datumPodnosenja"
})
public class Zig {
    @XmlElement(name="tip_ziga", required=true)
    private String tipZiga;

    @XmlElement(name="znak", required=true)
    private Znak znak;

    @XmlElementWrapper(name="brojevi_klasa_robe", required=true)
    @XmlElement(name="broj", required=true)
    private List<Integer> brojeviKlasaRobe = new ArrayList<Integer>();

    @XmlElement(name="zatrazeno_pravo", required=true)
    private String zatrazenoPravo;

    @XmlElement(name="broj_prijave_ziga", required=true)
    private int brojPrijaveZiga;

    @XmlElement(name="datum_podnosenja", required=true)
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate datumPodnosenja;

    public String getTipZiga() {
        return tipZiga;
    }

    public void setTipZiga(String tipZiga) {
        this.tipZiga = tipZiga;
    }

    public Znak getZnak() {
        return znak;
    }

    public void setZnak(Znak znak) {
        this.znak = znak;
    }

    public List<Integer> getBrojeviKlasaRobe() {
        return brojeviKlasaRobe;
    }

    public void setBrojeviKlasaRobe(List<Integer> brojeviKlasaRobe) {
        this.brojeviKlasaRobe = brojeviKlasaRobe;
    }

    public String getZatrazenoPravo() {
        return zatrazenoPravo;
    }

    public void setZatrazenoPravo(String zatrazenoPravo) {
        this.zatrazenoPravo = zatrazenoPravo;
    }

    public int getBrojPrijaveZiga() {
        return brojPrijaveZiga;
    }

    public void setBrojPrijaveZiga(int brojPrijaveZiga) {
        this.brojPrijaveZiga = brojPrijaveZiga;
    }

    public LocalDate getDatumPodnosenja() {
        return datumPodnosenja;
    }

    public void setDatumPodnosenja(LocalDate datumPodnosenja) {
        this.datumPodnosenja = datumPodnosenja;
    }


}
