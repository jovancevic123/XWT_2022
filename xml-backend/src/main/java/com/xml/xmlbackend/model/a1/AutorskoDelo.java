package com.xml.xmlbackend.model.a1;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "naslov", "deloPrerade", "vrstaDela", "formaZapiseDela", "stvorenoURadnomOdnosu",
        "nacinKoriscenja", "autori", "anonimanAutor"
})
public class AutorskoDelo {

    @XmlElement(name="naslov", required=true)
    private String naslov;

    @XmlElement(name="delo_prerade", required=true)
    private DeloPrerade deloPrerade;

    @XmlElement(name="vrsta_dela", required=true)
    private String vrstaDela;

    @XmlElement(name="forma_zapisa_dela", required=true)
    private String formaZapiseDela;

    @XmlElement(name="stvoren_u_radnom_odnosu", required=true)
    private boolean stvorenoURadnomOdnosu;

    @XmlElement(name="nacin_koriscenja", required=true)
    private String nacinKoriscenja;

    @XmlElementWrapper(name="autori", required=false)
    @XmlElement(name="autor", required=true)
    private List<Autor> autori = new ArrayList<Autor>();

    @XmlElement(name="anoniman_autor", required=true)
    private boolean anonimanAutor;

}
