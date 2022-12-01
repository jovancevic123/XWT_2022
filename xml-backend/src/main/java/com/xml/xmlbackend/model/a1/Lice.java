package com.xml.xmlbackend.model.a1;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlTransient
@XmlSeeAlso({FizickoLice.class, PravnoLice.class})
public abstract class Lice{
    @XmlElement(required = true)
    private Kontakt kontakt;
}
