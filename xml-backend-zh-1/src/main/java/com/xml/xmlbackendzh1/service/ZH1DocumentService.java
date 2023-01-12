package com.xml.xmlbackendzh1.service;

import com.xml.xmlbackendzh1.dao.ZH1DocumentDAO;
import com.xml.xmlbackendzh1.dto.RequestDto;
import com.xml.xmlbackendzh1.dto.TaksaDto;
import com.xml.xmlbackendzh1.model.zh1.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Getter
@Setter
public class ZH1DocumentService {

    private final ZH1DocumentDAO repository;

    @Autowired
    public ZH1DocumentService(ZH1DocumentDAO repository) {
        this.repository = repository;
    }

    public void findById(String resourceId) throws XMLDBException {
        this.repository.findById(resourceId);
    }

    public void addZig(RequestDto dto) throws JAXBException {

        Lice podnosilac;
        if(dto.getTipLicaPodnosilac() == 1){
            podnosilac = new FizickoLice(new Adresa(dto.getAdresaPodnosioca()), new Kontakt(dto.getKontaktPodnosioca()), dto.getImePodnosioca(), dto.getPrezimePodnosioca());
        }else{
            podnosilac = new PravnoLice(new Adresa(dto.getAdresaPodnosioca()), new Kontakt(dto.getKontaktPodnosioca()), dto.getPoslovnoImePodnosioca());
        }

        Lice punomocnik;
        if(dto.getTipLicaPunomocnik() == 1){
            punomocnik = new FizickoLice(new Adresa(dto.getAdresaPunomocnika()), new Kontakt(dto.getKontaktPunomocnika()), dto.getImePunomocnika(), dto.getPrezimePunomocnika());
        }else{
            punomocnik = new PravnoLice(new Adresa(dto.getAdresaPunomocnika()), new Kontakt(dto.getKontaktPunomocnika()), dto.getPoslovnoImePunomocnika());
        }

        Znak znak = new Znak();
        znak.setOpisZnaka(dto.getOpisZnaka());
        znak.setIzgledZnaka(new IzgledZnaka(dto.getIzgledZnaka()));
        znak.setTipZnaka(dto.getTipZnaka());
        znak.setBoje(dto.getBoje());
        znak.setPrevodZnaka(dto.getPrevodZnaka());
        znak.setTransliteracijaZnaka(dto.getTransliteracijaZnaka());

        Zig zig = new Zig();
        zig.setZnak(znak);
        zig.setDatumPodnosenja(LocalDate.now());
        zig.setTipZiga(dto.getTipZiga());
        zig.setBrojPrijaveZiga(Integer.parseInt(dto.getBrojPrijaveZiga()));
        zig.setZatrazenoPravo(dto.getZatrazenoPravo());
        zig.setBrojeviKlasaRobe(dto.getBrojeviKlasa());

        List<Taksa> placeneTakse = new ArrayList<>();
        for(TaksaDto taksaDto : dto.getPlaceneTakse()){
            placeneTakse.add(new Taksa(taksaDto));
        }

        Prilozi prilozi = new Prilozi();
        prilozi.setDokazOUplatiTakse(new Prilog(dto.getPriloziDto().getDokazOUplatiTakse()));
        prilozi.setOpstiAkt(new Prilog(dto.getPriloziDto().getOpstiAkt()));
        prilozi.setDokazOPravuPrvenstva(new Prilog(dto.getPriloziDto().getDokazOPravuPrvenstva()));
        prilozi.setPunomocje(new Prilog(dto.getPriloziDto().getPunomocje()));
        prilozi.setPrimerakZnaka(new Prilog(dto.getPriloziDto().getPrimerakZnaka()));
        prilozi.setPunomocjeNaknadnoDostavljeno(new Prilog(dto.getPriloziDto().getPunomocjeNaknadnoDostavljeno()));
        prilozi.setGeneralnoPpunomocje(new Prilog(dto.getPriloziDto().getGeneralnoPunomocje()));
        prilozi.setSpisakRobeIUsluga(new Prilog(dto.getPriloziDto().getSpisakRobeIUsluga()));

        Zavod zavod = new Zavod();

        Zahtev zahtev = new Zahtev();
        zahtev.setPodnosilac(podnosilac);
        zahtev.setPunomocnik(punomocnik);
        zahtev.setZavod(zavod);
        zahtev.setZig(zig);
        zahtev.setPlaceneTakse(placeneTakse);
        zahtev.setPrilozi(prilozi);
        zahtev.setNaziv("ZAHTEV ZA PRIZNANJE ZIGA");

        JAXBContext context = JAXBContext.newInstance(Zahtev.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(zahtev, System.out);
    }
}

