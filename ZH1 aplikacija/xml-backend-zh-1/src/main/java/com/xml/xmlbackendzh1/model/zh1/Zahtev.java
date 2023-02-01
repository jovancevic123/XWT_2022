package com.xml.xmlbackendzh1.model.zh1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.Manager;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "naziv", "zavod", "podnosilac", "punomocnik", "zig", "placeneTakse", "prilozi"
})
@XmlRootElement(name = "zahtev")
public class Zahtev {
    @XmlElement(name="naziv", required = true)
    private String naziv;

    @XmlElement(name="zavod", required=true)
    private Zavod zavod;

    @XmlElement(name="podnosilac", required = true)
    private Lice podnosilac;

    @XmlElement(name="punomocnik", required = true)
    private Lice punomocnik;

    @XmlElement(name = "zig", required = true)
    private Zig zig;

    @XmlElementWrapper(name="placene_takse", required=true)
    @XmlElement(name="taksa", required=true)
    private List<Taksa> placeneTakse = new ArrayList<Taksa>();

    @XmlElement(name="broj_resenja", required = true)
    private String brojResenja;

    @XmlElement(name="prilozi", required=true)
    private Prilozi prilozi = new Prilozi();

    @Override
    public String toString() {
        return "Zahtev{" +
                "naziv='" + naziv + '\'' +
                ", zavod=" + zavod +
                ", podnosilac=" + podnosilac +
                ", punomocnik=" + punomocnik +
                ", zig=" + zig +
                ", placeneTakse=" + placeneTakse +
                ", prilozi=" + prilozi +
                '}';
    }


}

