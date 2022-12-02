package com.xml.xmlbackend.model.a1;


import com.xml.xmlbackend.model.common.LocalDateAdapter;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "brojPrijave", "datumPodnosenja"
})
public class Prijava {

    @XmlElement(name="broj_prijave", required=true)
    private int brojPrijave;

    @XmlElement(name="datum_podnosenja", required=true)
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate datumPodnosenja;


}
