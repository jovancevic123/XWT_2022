package com.xml.backend.p1.model;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "tip", "lice"
})
public class Punomoc {

    @XmlElement(required = true)
    private Lice lice;

    @XmlElement(name = "tip", required=true)
    private String tip;

    public Lice getLice() {
        return lice;
    }

    public void setLice(Lice lice) {
        this.lice = lice;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
