package com.xml.backend.p1.model;
import com.xml.backend.p1.dto.RanijaPrijavaDto;
import com.xml.backend.p1.util.LocalDateAdapter;
import org.bouncycastle.crypto.ec.ECElGamalDecryptor;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "brojPrijave", "datumPodnosenja", "dvoslovnaOznaka"
})
public class RanijaPrijava {

    @XmlElement(name="broj_prijave", required=true)
    private String brojPrijave;

    @XmlElement(name="datum_podnosenja", required=true)
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate datumPodnosenja;

    @XmlElement(name="dvoslovna_oznaka", required=false)
    private String dvoslovnaOznaka;

    public RanijaPrijava(RanijaPrijavaDto dto) {
        this.brojPrijave = dto.getBrojPrijave();
        try {
            this.datumPodnosenja = LocalDate.parse(dto.getDatumPodnosenja());
        }catch(Exception e){
            this.datumPodnosenja = LocalDate.now();
        }
        this.dvoslovnaOznaka = dto.getDvoslovnaOznaka();
    }

    public RanijaPrijava(){}
}
