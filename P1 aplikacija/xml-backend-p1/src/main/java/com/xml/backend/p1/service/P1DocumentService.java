package com.xml.backend.p1.service;

import com.xml.backend.p1.dao.P1DocumentDAO;
import com.xml.backend.p1.dto.RanijaPrijavaDto;
import com.xml.backend.p1.dto.RequestDto;
import com.xml.backend.p1.model.*;
import lombok.*;
import org.checkerframework.checker.units.qual.K;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Getter
@Setter
public class P1DocumentService {

    private final P1DocumentDAO repository;

    @Autowired
    public P1DocumentService(P1DocumentDAO repository) {
        this.repository = repository;
    }

    public void findById(String resourceId) throws XMLDBException {
        this.repository.findById(resourceId);
    }

    public void addPatent(RequestDto dto) throws JAXBException {
        int brojPrijave = (int) new Date().getTime();
        Prijava prijava = new Prijava(brojPrijave, LocalDate.now(), null, dto.getVrstaPrijave() == 1 ? "dopunska" : "izdvojena" , 0, null);
        Zavod zavod = new Zavod();
        Pronalazak pronalazak = new Pronalazak(dto.getNazivSRB(), dto.getNazivENG());

        Lice podnosilacLice;
        if(dto.getTipLicaPodnosilac() == 1){
            podnosilacLice = new FizickoLice(new Adresa(dto.getPodnosilacAdresa()), new Kontakt(dto.getPodnosilacKontakt()), dto.getImePodnosilac(), dto.getPrezimePodnosilac());
        }else{
            podnosilacLice = new PravnoLice(new Adresa(dto.getPodnosilacAdresa()), new Kontakt(dto.getPodnosilacKontakt()), dto.getPoslovnoImePodnosilac());
        }

        Lice pronalazacLice;
        if(dto.getTipLicaPronalazac() == 1){
            pronalazacLice = new FizickoLice(new Adresa(dto.getPronalazacAdresa()), new Kontakt(dto.getPronalazacKontakt()), dto.getImePronalazac(), dto.getPrezimePronalazac());
        }else{
            pronalazacLice = new PravnoLice(new Adresa(dto.getPronalazacAdresa()), new Kontakt(dto.getPronalazacKontakt()), dto.getPoslovnoImePronalazac());
        }

        Lice punomocLice = null;
        if(dto.getTipLicaPunomoc() == 1){
            podnosilacLice = new FizickoLice(new Adresa(dto.getPunomocAdresa()), new Kontakt(dto.getPunomocKontakt()), dto.getImePunomoc(), dto.getPrezimePunomoc());
        }else{
            podnosilacLice = new PravnoLice(new Adresa(dto.getPunomocAdresa()), new Kontakt(dto.getPunomocKontakt()), dto.getPoslovnoImePunomoc());
        }

        String nacinDostavljanja = dto.getElektronskaDostava() == 1 ? "elektronska_forma" : "papirna_forma";
        String tipPunomocnika = dto.getTipPunomocnika() == 1 ? "punomocnik_za_zastupanje" : "punomocnik_za_prijem_pismena";

        Podnosilac podnosilac = new Podnosilac(podnosilacLice, dto.isPodnosilacJePronalazac(), dto.getDrzavljanstvoPodnosilac());
        Pronalazac pronalazac = new Pronalazac(pronalazacLice, dto.isNavedenUPrijavi());
        Punomoc punomoc = new Punomoc(punomocLice, tipPunomocnika);

        List<RanijaPrijava> ranijePrijave = new ArrayList<>();
        for(RanijaPrijavaDto rp : dto.getRanijePrijave()){
            ranijePrijave.add(new RanijaPrijava(rp));
        }


        Zahtev zahtev = new Zahtev();
        zahtev.setPodnosilac(podnosilac);
        zahtev.setPronalazac(pronalazac);
        zahtev.setPunomoc(punomoc);
        zahtev.setPrijava(prijava);
        zahtev.setZavod(zavod);
        zahtev.setPronalazak(pronalazak);
        zahtev.setNacinDostavljanja(nacinDostavljanja);
        zahtev.setRanijePrijave(ranijePrijave);

        JAXBContext context = JAXBContext.newInstance(Zahtev.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(zahtev, System.out);
    }
}
