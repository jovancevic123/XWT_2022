package com.xml.backend.p1.service;

import com.xml.backend.p1.dao.P1DocumentDAO;
import com.xml.backend.p1.dto.*;
import com.xml.backend.p1.model.*;
import com.xml.backend.p1.transformers.XmlTransformer;
import lombok.*;
import org.exist.source.StringSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Getter
@Setter
public class P1DocumentService {

    private final P1DocumentDAO repository;
    private final XmlTransformer transformer;
    private final MetadataService metadataService;
    private final ResenjeMetadataService resenjeMetadataService;

    @Autowired
    public P1DocumentService(P1DocumentDAO repository, XmlTransformer transformer, MetadataService metadataService,
                             ResenjeMetadataService resenjeMetadataService) {
        this.repository = repository;
        this.transformer = transformer;
        this.metadataService = metadataService;
        this.resenjeMetadataService = resenjeMetadataService;
    }

    public XMLResource findZahtevById(String resourceId) throws XMLDBException {
        return this.repository.findById(resourceId, "/db/patent/zahtevi");
    }

    public XMLResource findResenjeById(String resourceId) throws XMLDBException {
        return this.repository.findById(resourceId, "/db/patent/resenja");
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

    public void addPatentXonomy(Zahtev zahtev) throws JAXBException {
        int brojPrijave = (int) new Date().getTime();
        zahtev.getPrijava().setBrojPrijave(brojPrijave);
        zahtev.getPrijava().setDatumPrijema(LocalDate.now());
        Zavod zavod = new Zavod();
        zahtev.setZavod(zavod);


        JAXBContext context = JAXBContext.newInstance(Zahtev.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(zahtev, System.out);
    }

    public ByteArrayResource getRequestPDF(String brojPrijave) throws XMLDBException, IOException {
        XMLResource res = this.findZahtevById(brojPrijave);
        String xmlData = res.getContent().toString();

        transformer.transformToPdf(xmlData);
        File f = new File("src/main/resources/xml/GeneratedPDF.pdf");
        byte[] fileContent = Files.readAllBytes(f.toPath());
        ByteArrayResource body = new ByteArrayResource(fileContent);
        return body;
    }

    public String getRequestHTML(String brojPrijave) throws XMLDBException, IOException {
        XMLResource res = this.findZahtevById(brojPrijave);
        String xmlData = res.getContent().toString();

        transformer.transformToHtml(xmlData);
        String html = Files.readString(Paths.get("src/main/resources/xml/GeneratedHTML.html"));
        return html;
    }

    public List<PendingRequestDto> getPendingRequests() {
        this.metadataService.metaDataSelect(new SearchMetadataDto("999", "-1"));
        return new ArrayList<>();
    }

    public void approveRequest(ResponseToPendingRequestDto dto) throws Exception {
        Resenje resenje = makeResenjeFromDto(dto);

        JAXBContext context = JAXBContext.newInstance(Resenje.class);
        StringWriter stringWriter = new StringWriter();
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(resenje, stringWriter);

        this.repository.save(resenje.getBrojResenja(), stringWriter.toString(), "/db/patent/resenja");
        uploadResenjeMetadata(stringWriter.toString());
        updateBrojResenjaInZahtev(dto.getBrojPrijave(), resenje.getBrojResenja());
    }

    public void rejectRequest(ResponseToPendingRequestDto dto) throws Exception {
        Resenje resenje = makeResenjeFromDto(dto);
        resenje.setObrazlozenje(dto.getObrazlozenje());

        JAXBContext context = JAXBContext.newInstance(Resenje.class);
        StringWriter stringWriter = new StringWriter();
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(resenje, stringWriter);

        this.repository.save(resenje.getBrojResenja(), stringWriter.toString(), "/db/patent/resenja");
        uploadResenjeMetadata(stringWriter.toString());
        updateBrojResenjaInZahtev(dto.getBrojPrijave(), resenje.getBrojResenja());
    }

    private void updateBrojResenjaInZahtev(String brojPrijave, String brojResenja) throws Exception {
        XMLResource res = this.repository.findById(brojPrijave + ".xml", "/db/patent/");
        JAXBContext context = JAXBContext.newInstance(Zahtev.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(new StringReader((String) res.getContent()));
        Zahtev zahtev = (Zahtev) unmarshaller.unmarshal(reader);
        zahtev.setBrojResenja(brojResenja);

        StringWriter stringWriter = new StringWriter();
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(zahtev, stringWriter);

        this.repository.save(brojPrijave, stringWriter.toString(), "/db/patent/zahtevi");
    }

    private void uploadResenjeMetadata(String xmlData) throws IOException {
        String xsltFIlePath = "./src/main/resources/xml/resenje-metadata.xsl";
        String outputPath = "./src/main/resources/static/rdf/";

        resenjeMetadataService.transformRDF(xmlData, xsltFIlePath, outputPath); // 1. xml u obliku string-a
        String resultMeta = resenjeMetadataService.extractMetadataToRdf(new FileInputStream(new File("./src/main/resources/static/rdf")), "./src/main/resources/static/extracted_rdf.xml");

        resenjeMetadataService.uploadMetadata("./src/main/resources/static/extracted_rdf.xml", "/resenje");
    }

    private Resenje makeResenjeFromDto(ResponseToPendingRequestDto dto){
        long brojResenja = (long) new Date().getTime();
        Resenje resenje = new Resenje();

        resenje.setBrojPrijave(dto.getBrojPrijave());
        resenje.setDatumOdgovora(LocalDate.now());
        resenje.setBrojResenja(Long.toString(brojResenja));
        resenje.setImeSluzbenika(dto.getImeSluzbenika());
        resenje.setPrezimeSluzbenika(dto.getPrezimeSluzbenika());

        return resenje;
    }
}
