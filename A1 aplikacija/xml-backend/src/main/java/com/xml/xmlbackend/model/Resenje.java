package com.xml.xmlbackend.model;

import com.xml.xmlbackend.dto.ResponseToPendingRequestDto;
import com.xml.xmlbackend.model.common.LocalDateAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "brojResenja", "brojPrijave", "obrazlozenje",
        "imeSluzbenika", "prezimeSluzbenika", "datumOdgovora", "prihvacena"
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

    @XmlElement(name = "prihvacena", required=true)
    private boolean prihvacena;

    public Resenje(ResponseToPendingRequestDto dto){
        long brojResenja = (long) new Date().getTime();

        this.setBrojPrijave(dto.getBrojPrijave());
        this.setDatumOdgovora(LocalDate.now());
        this.setBrojResenja(Long.toString(brojResenja));
        this.setImeSluzbenika(dto.getImeSluzbenika().split(" ")[0]);
        this.setPrezimeSluzbenika(dto.getImeSluzbenika().split(" ")[1]);
    }
}
