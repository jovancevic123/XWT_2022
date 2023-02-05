package com.xml.backend.p1.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RanijaPrijavaDto {

    private String brojPrijave;
    private String datumPodnosenja;
    private String dvoslovnaOznaka;
}
