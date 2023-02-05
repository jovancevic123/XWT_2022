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
        "vrstaPriloga", "src"
})
public class Prilog {

    @XmlAttribute(required=true, name = "vrsta_priloga")
    private String vrstaPriloga;

    @XmlAttribute(required=true)
    private String src;

    public String getVrstaPriloga() {
        return vrstaPriloga;
    }

    public void setVrstaPriloga(String vrstaPriloga) {
        this.vrstaPriloga = vrstaPriloga;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
