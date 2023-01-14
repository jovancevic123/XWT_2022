package com.xml.backend.p1.model;

import com.xml.backend.p1.util.LocalDateAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "brojResenja", "brojPrijave", "obrazlozenje",
        "imeSluzbenika", "prezimeSluzbenika", "datumOdgovora"
})
@XmlRootElement(name = "resenje")
public class Resenje {

    @XmlElement(name = "broj_resenja", required=true)
    private String brojResenja;

    @XmlElement(name = "broj_prijave", required=true)
    private String brojPrijave;

    @XmlElement(name = "obrazlozenje", required=false)
    private String obrazlozenje;

    @XmlElement(name = "ime_sluzbenika", required=true)
    private String imeSluzbenika;

    @XmlElement(name = "prezime_sluzbenika", required=true)
    private String prezimeSluzbenika;

    @XmlElement(name = "datum_odgovora", required=true)
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate datumOdgovora;
}
