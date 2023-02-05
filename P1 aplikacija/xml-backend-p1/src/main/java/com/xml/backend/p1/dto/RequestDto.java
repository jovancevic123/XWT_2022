package com.xml.backend.p1.dto;

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

    private String nazivSRB;
    private String nazivENG;

    private String imePodnosilac;
    private String prezimePodnosilac;
    private String drzavljanstvoPodnosilac;
    private String poslovnoImePodnosilac;
    private int tipLicaPodnosilac;
    private boolean podnosilacJePronalazac;

    private String imePronalazac;
    private String prezimePronalazac;
    private String drzavljanstvoPronalazac;
    private String poslovnoImePronalazac;
    private int tipLicaPronalazac;
    private boolean navedenUPrijavi;

    private String imePunomoc;
    private String prezimePunomoc;
    private String drzavljanstvoPunomoc;
    private String poslovnoImePunomoc;
    private int tipLicaPunomoc;
    private int tipPunomocnika;

    private boolean drugaAdresaDostave;
    private int elektronskaDostava;
    private int vrstaPrijave;

    private AdresaDto podnosilacAdresa;
    private AdresaDto pronalazacAdresa;
    private AdresaDto punomocAdresa;
    private AdresaDto adresaDostave;

    private KontaktDto podnosilacKontakt;
    private KontaktDto pronalazacKontakt;
    private KontaktDto punomocKontakt;

    private List<RanijaPrijavaDto> ranijePrijave;
}
