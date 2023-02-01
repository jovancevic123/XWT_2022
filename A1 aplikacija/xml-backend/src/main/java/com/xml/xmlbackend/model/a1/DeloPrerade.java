package com.xml.xmlbackend.model.a1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "naslovDelaPrerade", "autori"
})
public class DeloPrerade {

    @XmlElement(name="naslov_dela_prerade", required=true)
    private String naslovDelaPrerade;

    @XmlElementWrapper(name="autori", required=false)
    @XmlElement(name="autor", required=true)
    private List<Autor> autori = new ArrayList<Autor>();

    public String getNaslovDelaPrerade() {
        return naslovDelaPrerade;
    }

    public void setNaslovDelaPrerade(String naslovDelaPrerade) {
        this.naslovDelaPrerade = naslovDelaPrerade;
    }

    public List<Autor> getAutori() {
        return autori;
    }

    public void setAutori(List<Autor> autori) {
        this.autori = autori;
    }
}
