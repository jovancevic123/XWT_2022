package com.xml.backend.p1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@XmlTransient
@XmlSeeAlso({
        FizickoLice.class,
        PravnoLice.class
})
public class Lice {

    protected Adresa adresa;
    protected Kontakt kontakt;
}
