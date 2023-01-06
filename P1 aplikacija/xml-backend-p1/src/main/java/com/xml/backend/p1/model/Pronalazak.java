package com.xml.backend.p1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "nazivPronalaskaSRB", "nazivPronalaskaENG" })
public class Pronalazak {

    @XmlElement(name="naziv_pronalaska_srb", required = true)
    private String nazivPronalaskaSRB;

    @XmlElement(name="naziv_pronalaska_eng", required = true)
    private String nazivPronalaskaENG;
}
