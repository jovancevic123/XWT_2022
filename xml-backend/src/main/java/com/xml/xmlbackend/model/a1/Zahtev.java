package com.xml.xmlbackend.model.a1;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "prijava", "podnosilac", "punomocnik", "autorskoDelo", "prilozi"
})
@XmlRootElement(name = "zahtev")
public class Zahtev {
    @XmlElement(required = true)
    private Prijava prijava;

    @XmlElement(required = false)
    private Podnosilac podnosilac;


    @XmlElements({
            @XmlElement(name="punomocnik",type=FizickoLice.class),
            @XmlElement(name="punomocnik",type=PravnoLice.class),
    })
    private Lice punomocnik;

    @XmlElement(name = "autorsko_delo",required = true)
    private AutorskoDelo autorskoDelo;

    @XmlElementWrapper(name="prilozi", required=true)
    @XmlElement(name="prilog", required=true)
    private List<Prilog> prilozi = new ArrayList<Prilog>();
}
