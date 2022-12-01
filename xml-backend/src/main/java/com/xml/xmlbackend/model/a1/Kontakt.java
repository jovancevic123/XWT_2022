package com.xml.xmlbackend.model.a1;


import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "telefon", "email", "fax"
})
public class Kontakt {
    @XmlElement(name="telefon", required=true)
    private String telefon;

    @XmlElement(name="email", required=true)
    private String email;

    @XmlElement(name="fax", required=true)
    private String fax;
}
