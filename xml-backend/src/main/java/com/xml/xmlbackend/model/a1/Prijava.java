package com.xml.xmlbackend.model.a1;


import javax.xml.bind.annotation.*;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "brojPrijave", "datumPodnosenja"
})
public class Prijava {

    @XmlElement(name="broj_prijave", required=true)
    private int brojPrijave;

    @XmlElement(name="datum_podnosenja", required=true)
    private LocalDate datumPodnosenja;
}
