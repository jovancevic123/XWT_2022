package com.xml.xmlbackendzh1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class RequestDto {

    private String imePodnosioca;
    private String prezimePodnosioca;
    private String poslovnoImePodnosioca;
    private AdresaDto adresaPodnosioca;
    private KontaktDto kontaktPodnosioca;
    private int tipLicaPodnosilac;

    private String imePunomocnika;
    private String prezimePunomocnika;
    private String poslovnoImePunomocnika;
    private AdresaDto adresaPunomocnika;
    private KontaktDto kontaktPunomocnika;
    private int tipLicaPunomocnik;

    private String tipZiga;
    private String tipZnaka;
    private String izgledZnaka;
    private List<String> boje;
    private String transliteracijaZnaka;
    private String prevodZnaka;
    private String opisZnaka;
    private List<Integer> brojeviKlasa;
    private String zatrazenoPravo;
    private boolean znakNijeCirLat;

    private List<TaksaDto> placeneTakse;
}

