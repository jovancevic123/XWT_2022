package com.xml.xmlbackendzh1.model.zh1;
import com.xml.xmlbackendzh1.dto.KontaktDto;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "telefon", "email", "fax"
})
public class Kontakt {
    @XmlElement(name="telefon", required=true)
    private String telefon;

    @XmlElement(name="email", required=true)
    private String email;

    @XmlElement(name="fax", required=false)
    private String fax;

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Kontakt(KontaktDto dto){
        this.telefon = dto.getBroj();
        this.email = dto.getEmail();
        this.fax = dto.getFax();
    }
}
