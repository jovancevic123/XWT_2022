package com.xml.xmlbackendzh1.service;

import com.xml.xmlbackendzh1.dao.ExistDao;
import com.xml.xmlbackendzh1.dto.RequestDto;
import com.xml.xmlbackendzh1.dto.TaksaDto;
import com.xml.xmlbackendzh1.exceptions.FormatNotValidException;
import com.xml.xmlbackendzh1.model.zh1.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Getter
@Setter
public class ZH1DocumentService {

    private final ExistDao repository;
    private final ExistService existService;
    private final MetadataService metadataService;

    @Autowired
    public ZH1DocumentService(ExistDao repository, ExistService existService, MetadataService metadataService) {
        this.repository = repository;
        this.existService = existService;
        this.metadataService = metadataService;
    }

    public XMLResource findZahtevById(String resourceId) throws XMLDBException {
        return this.repository.findById(resourceId + ".xml", "/db/zig/zahtevi");
    }

    public void addZahtevZig(RequestDto dto) throws Exception {
        int brojPrijave = (int) new Date().getTime();

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
        zig.setTipZiga(dto.getTipZiga());
        zig.setZatrazenoPravo(dto.getZatrazenoPravo());
        zig.setBrojeviKlasaRobe(dto.getBrojeviKlasa());
        zig.setBrojPrijaveZiga(brojPrijave);
        zig.setDatumPodnosenja(LocalDate.now());

        List<Taksa> placeneTakse = new ArrayList<>();
        for(TaksaDto taksaDto : dto.getPlaceneTakse()){
            placeneTakse.add(new Taksa(taksaDto));
        }

        Zavod zavod = new Zavod();
        zavod.setNaziv("ZAHTEV ZA PRIZNANJE ZIGA");
        Adresa adresaZavoda = new Adresa();
        adresaZavoda.setUlica("Kneginje Ljubice");
        adresaZavoda.setBroj(5);
        adresaZavoda.setPostanskiBroj(11000);
        adresaZavoda.setMesto("Beograd");
        adresaZavoda.setDrzava("Srbija");
        zavod.setAdresa(adresaZavoda);

        Zahtev zahtev = new Zahtev();
        zahtev.setPodnosilac(podnosilac);
        zahtev.setPunomocnik(punomocnik);
        zahtev.setZavod(zavod);
        zahtev.setZig(zig);
        zahtev.setPlaceneTakse(placeneTakse);
        zahtev.setNaziv("ZAHTEV ZA PRIZNANJE ZIGA");

        Prilozi prilozi = new Prilozi();
        Prilog primerakZnaka = new Prilog("");
        Prilog spisakRobaIUsluga = new Prilog("");
        Prilog generalnoPunomocje = new Prilog("");
        Prilog punomocje = new Prilog("");
        Prilog punomocjeNaknadnoDostavljeno = new Prilog("");
        Prilog dokazOPravuPrvenstva = new Prilog("");
        Prilog opstiAkt = new Prilog("");
        Prilog dokazOUplatiTakse = new Prilog("");

        prilozi.setPrimerakZnaka(primerakZnaka);
        prilozi.setSpisakRobeIUsluga(spisakRobaIUsluga);
        prilozi.setGeneralnoPpunomocje(generalnoPunomocje);
        prilozi.setPunomocje(punomocje);
        prilozi.setPunomocjeNaknadnoDostavljeno(punomocjeNaknadnoDostavljeno);
        prilozi.setDokazOPravuPrvenstva(dokazOPravuPrvenstva);
        prilozi.setOpstiAkt(opstiAkt);
        prilozi.setDokazOUplatiTakse(dokazOUplatiTakse);

        zahtev.setPrilozi(prilozi);

        JAXBContext context = JAXBContext.newInstance(Zahtev.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//        m.marshal(zahtev, System.out);

        String newFilePath = "src/main/resources/xml/generated/" + zahtev.getZig().getBrojPrijaveZiga() + ".xml";
        File file = new File(newFilePath);
        m.marshal(zahtev, file);
        boolean valid = this.existService.validateXMLSchema("src/main/resources/xml/ZH-1.xsd", newFilePath);
        if(valid){
            String xmlData = Files.readString(Paths.get(newFilePath));
            this.repository.save(zahtev.getZig().getBrojPrijaveZiga() + "", xmlData, "/db/zig/zahtevi");
            this.uploadZahtevMetadata(xmlData);
            return;
        }
        throw new FormatNotValidException();
    }

    private void uploadZahtevMetadata(String xmlData) throws IOException {
        String xsltFIlePath = "./src/main/resources/xml/metadata.xsl";
        String outputPath = "./src/main/resources/static/rdf/";

        metadataService.transformRDF(xmlData, xsltFIlePath, outputPath); // 1. xml u obliku string-a
        String resultMeta = metadataService.extractMetadataToRdf(new FileInputStream(new File("./src/main/resources/static/rdf")), "./src/main/resources/static/extracted_rdf.xml");

        metadataService.uploadResenjeMetadata("./src/main/resources/static/extracted_rdf.xml", "/graph/metadata/zh1");
    }
}

