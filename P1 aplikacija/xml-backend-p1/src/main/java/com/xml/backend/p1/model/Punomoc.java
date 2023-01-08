package com.xml.backend.p1.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "tip", "lice"
})
public class Punomoc {

    @XmlElement(required = true)
    private Lice lice;

    @XmlElement(name = "tip", required=true)
    private String tip;
}
