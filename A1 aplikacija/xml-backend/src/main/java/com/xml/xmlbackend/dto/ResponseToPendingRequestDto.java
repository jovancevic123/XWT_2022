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
public class ResponseToPendingRequestDto {
    private String brojPrijave;
    private String obrazlozenje;
    private String imeSluzbenika;
    private boolean prihvacena;
}