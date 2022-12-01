package com.xml.xmlbackend.model.a1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"ime", "prezime", "drzavljanstvo"})
public class FizickoLice extends Lice{

    @XmlElement(required = true)
    private String ime;

    @XmlElement(required = true)
    private String prezime;

    @XmlElement(required = true)
    private String drzavljanstvo;
}
