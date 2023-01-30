package com.xml.backend.p1.model;

import com.xml.backend.p1.util.LocalDateAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "brojPrijave", "datumPrijema", "priznatiDatumPodnosenja", "vrstaPrijave",
//        "brojPrvobitnePrijave", "datumPodnosenjaPrvobitnePrijave"
})
public class Prijava {

    @XmlElement(name="broj_prijave", required = true)
    private int brojPrijave;

    @XmlElement(name="datum_prijema", required = true)
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate datumPrijema;

    @XmlElement(name="priznati_datum_podnosenja", required = false)
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate priznatiDatumPodnosenja;

    @XmlElement(name="vrsta_prijave", required = true)
    private String vrstaPrijave;

//    @XmlElement(name="broj_prvobitne_prijave", required = false)
//    private int brojPrvobitnePrijave;
//
//    @XmlElement(name="datum_podnosenja_prvobitne_prijave", required = false)
//    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
//    private LocalDate datumPodnosenjaPrvobitnePrijave;

//    public Prijava(int brojPrijave, LocalDate datumPrijema, LocalDate priznatiDatumPodnosenja, String vrstaPrijave) {
//        this.brojPrijave = brojPrijave;
//        this.datumPrijema = datumPrijema;
//        this.priznatiDatumPodnosenja = priznatiDatumPodnosenja;
//        this.vrstaPrijave = vrstaPrijave;
//    }
}
