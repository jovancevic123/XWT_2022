package com.xml.xmlbackend.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class ZahtevRequestDto {
    private AutorskoDeloDto autorskoDelo;
    private DeloPreradeDto deloPrerade;
    private LiceDto podnosilac;
    private LiceDto punomocnik;
}
