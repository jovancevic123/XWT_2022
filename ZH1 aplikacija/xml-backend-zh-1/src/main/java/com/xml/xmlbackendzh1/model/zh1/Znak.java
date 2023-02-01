package com.xml.xmlbackendzh1.model.zh1;

import javax.xml.bind.annotation.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "tipZnaka", "izgledZnaka", "boje", "opisZnaka", "transliteracijaZnaka",
        "prevodZnaka"
})
public class Znak {

    @XmlElement(name="tip_znaka", required=true)
    private String tipZnaka;

    @XmlElement(name="izgled_znaka", required=true)
    private IzgledZnaka izgledZnaka;

    @XmlElementWrapper(name="boje", required=true)
    @XmlElement(name="boja", required=true)
    private List<String> boje = new ArrayList<String>();

    @XmlElement(name="opis_znaka", required=true)
    private String opisZnaka;

    @XmlElement(name="transliteracija_znaka", required=false)
    private String transliteracijaZnaka;

    @XmlElement(name="prevod_znaka", required=false)
    private String prevodZnaka;

    public String getTipZnaka() {
        return tipZnaka;
    }

    public void setTipZnaka(String tipZnaka) {
        this.tipZnaka = tipZnaka;
    }

    public IzgledZnaka getIzgledZnaka() {
        return izgledZnaka;
    }

    public void setIzgledZnaka(IzgledZnaka izgledZnaka) {
        this.izgledZnaka = izgledZnaka;
    }

    public List<String> getBoje() {
        return boje;
    }

    public void setBoje(List<String> boje) {
        this.boje = boje;
    }

    public String getOpisZnaka() {
        return opisZnaka;
    }

    public void setOpisZnaka(String opisZnaka) {
        this.opisZnaka = opisZnaka;
    }

    public String getTransliteracijaZnaka() {
        return transliteracijaZnaka;
    }

    public void setTransliteracijaZnaka(String transliteracijaZnaka) {
        this.transliteracijaZnaka = transliteracijaZnaka;
    }

    public String getPrevodZnaka() {
        return prevodZnaka;
    }

    public void setPrevodZnaka(String prevodZnaka) {
        this.prevodZnaka = prevodZnaka;
    }


}
