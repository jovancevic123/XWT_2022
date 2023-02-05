package com.xml.xmlbackendzh1.model.zh1;
import com.xml.xmlbackendzh1.dto.TaksaDto;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "valuta", "tipTakse", "iznos"
})
public class Taksa {
    @XmlAttribute(required=true, name = "valuta")
    private String valuta;

    @XmlElement(name="tip_takse", required=true)
    private String tipTakse;

    @XmlElement(name="iznos", required=true)
    private double iznos;

    public Taksa(){}

    public Taksa(TaksaDto taksaDto) {
        this.valuta = "RSD";
        this.tipTakse = taksaDto.getTipTakse();
        this.iznos = Double.parseDouble(taksaDto.getIznos());
    }

    public String getValuta() {
        return valuta;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    public String getTipTakse() {
        return tipTakse;
    }

    public void setTipTakse(String tipTakse) {
        this.tipTakse = tipTakse;
    }

    public double getIznos() {
        return iznos;
    }

    public void setIznos(double iznos) {
        this.iznos = iznos;
    }


}
