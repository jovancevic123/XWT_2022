package com.xml.xmlbackend.service;

import com.xml.xmlbackend.dao.A1DocumentDAO;
import com.xml.xmlbackend.dto.AutorDto;
import com.xml.xmlbackend.dto.ResponseToPendingRequestDto;
import com.xml.xmlbackend.dto.SearchResultDto;
import com.xml.xmlbackend.dto.ZahtevRequestDto;
import com.xml.xmlbackend.exception.FormatNotValidException;
import com.xml.xmlbackend.model.Resenje;
import com.xml.xmlbackend.model.a1.*;
import com.xml.xmlbackend.transformers.XmlTransformer;
import lombok.RequiredArgsConstructor;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import org.json.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class A1DocumentService {

    private final ExistService existService;
    private final A1DocumentDAO repository;
    private final MetadataService metadataService;
    private final XmlTransformer transformer;

    public void addAutorskoDelo(ZahtevRequestDto dto) throws Exception {
        int brojPrijave = (int)new Date().getTime();
        Prijava prijava = new Prijava();
        prijava.setBrojPrijave(brojPrijave);
        prijava.setDatumPodnosenja(LocalDate.now());

        Zavod zavod = new Zavod();
        Adresa adresa = new Adresa();
        adresa.setUlica("Kneginje Ljubice");
        adresa.setBroj(5);
        adresa.setPostanskiBroj(11000);
        adresa.setMesto("Beograd");
        adresa.setDrzava("Srbija");
        zavod.setAdresa(adresa);
        zavod.setNaziv("Zavod za intelektualnu svojinu");

        Podnosilac podnosilac = new Podnosilac();
        podnosilac.setPodnosilacJeAutor(dto.getAutorskoDelo().isPodnosilacJeAutor());
        Kontakt kontaktPodnosilac = new Kontakt();
        kontaktPodnosilac.setTelefon(dto.getPodnosilac().getKontakt().getBroj());
        kontaktPodnosilac.setEmail(dto.getPodnosilac().getKontakt().getEmail());
        kontaktPodnosilac.setFax(dto.getPodnosilac().getKontakt().getFax());

        //podnosilac je fizicko lice
        if(dto.getPodnosilac().getIme() != null){
            FizickoLice fizickoLicePodnosilac = new FizickoLice();
            fizickoLicePodnosilac.setIme(dto.getPodnosilac().getIme());
            fizickoLicePodnosilac.setPrezime(dto.getPodnosilac().getPrezime());
            fizickoLicePodnosilac.setDrzavljanstvo(dto.getPodnosilac().getDrzavljanstvo());

            Adresa podnosilacAdresa = new Adresa();
            podnosilacAdresa.setUlica(dto.getPodnosilac().getAdresa().getUlica());
            podnosilacAdresa.setBroj(dto.getPodnosilac().getAdresa().getBroj());
            podnosilacAdresa.setPostanskiBroj(dto.getPodnosilac().getAdresa().getPostanskiBroj());
            podnosilacAdresa.setMesto(dto.getPodnosilac().getAdresa().getMesto());
            podnosilacAdresa.setDrzava(dto.getPodnosilac().getAdresa().getDrzava());
            fizickoLicePodnosilac.setAdresa(podnosilacAdresa);
            fizickoLicePodnosilac.setKontakt(kontaktPodnosilac);
            podnosilac.setLice(fizickoLicePodnosilac);
        }
        else{
            PravnoLice pravnoLicePodnosilac = new PravnoLice();
            pravnoLicePodnosilac.setKontakt(kontaktPodnosilac);
            pravnoLicePodnosilac.setPoslovnoIme(dto.getPodnosilac().getPoslovnoIme());
            pravnoLicePodnosilac.setSediste(dto.getPodnosilac().getSediste());
            podnosilac.setLice(pravnoLicePodnosilac);
        }

        Lice punomocnik = null;
        if(dto.getPunomocnik() != null){
            Kontakt punomocnikKontakt = new Kontakt();
            punomocnikKontakt.setEmail(dto.getPunomocnik().getKontakt().getEmail());
            punomocnikKontakt.setTelefon(dto.getPunomocnik().getKontakt().getBroj());
            punomocnikKontakt.setFax(dto.getPunomocnik().getKontakt().getFax());
            if(dto.getPunomocnik().getIme() != null){
                FizickoLice fizickoLicePunomocnik = new FizickoLice();
                fizickoLicePunomocnik.setIme(dto.getPunomocnik().getIme());
                fizickoLicePunomocnik.setPrezime(dto.getPunomocnik().getPrezime());
                fizickoLicePunomocnik.setDrzavljanstvo(dto.getPunomocnik().getDrzavljanstvo());

                Adresa punomocnikAdresa = new Adresa();
                punomocnikAdresa.setUlica(dto.getPunomocnik().getAdresa().getUlica());
                punomocnikAdresa.setBroj(dto.getPunomocnik().getAdresa().getBroj());
                punomocnikAdresa.setPostanskiBroj(dto.getPunomocnik().getAdresa().getPostanskiBroj());
                punomocnikAdresa.setMesto(dto.getPunomocnik().getAdresa().getMesto());
                punomocnikAdresa.setDrzava(dto.getPunomocnik().getAdresa().getDrzava());
                fizickoLicePunomocnik.setAdresa(punomocnikAdresa);
                fizickoLicePunomocnik.setKontakt(punomocnikKontakt);
                punomocnik = fizickoLicePunomocnik;

            }
            else{
                PravnoLice pravnoLicePunomocnik = new PravnoLice();
                pravnoLicePunomocnik.setKontakt(punomocnikKontakt);
                pravnoLicePunomocnik.setPoslovnoIme(dto.getPunomocnik().getPoslovnoIme());
                pravnoLicePunomocnik.setSediste(dto.getPunomocnik().getSediste());
                punomocnik = pravnoLicePunomocnik;
            }
        }

        //delo prerade
        DeloPrerade deloPrerade = null;
        if(dto.getAutorskoDelo().isJeDeloPrerade()){
            deloPrerade = new DeloPrerade();
            deloPrerade.setNaslovDelaPrerade(dto.getDeloPrerade().getNaslovDela());
            List<Autor> autoriPrerade = new ArrayList<>();
            for (AutorDto autorDto: dto.getDeloPrerade().getAutoriDelaPrerade()) {
                Autor autor = new Autor();
                autor.setIme(autorDto.getIme());
                autor.setPrezime(autor.getPrezime());
                autor.setDrzavljanstvo(autorDto.getDrzavljanstvo());
                autor.setPseudonim(autorDto.getPseudonim());

                Adresa autorAdresa = new Adresa();
                autorAdresa.setUlica(autorDto.getAdresa().getUlica());
                autorAdresa.setBroj(autorDto.getAdresa().getBroj());
                autorAdresa.setPostanskiBroj(autorDto.getAdresa().getPostanskiBroj());
                autorAdresa.setMesto(autorDto.getAdresa().getMesto());
                autorAdresa.setDrzava(autorDto.getAdresa().getDrzava());

                Kontakt autorKontakt = new Kontakt();
                autorKontakt.setEmail(autorDto.getKontakt().getEmail());
                autorKontakt.setTelefon(autorDto.getKontakt().getBroj());
                autorKontakt.setFax(autorDto.getKontakt().getFax());
                autor.setAdresa(autorAdresa);
                autor.setKontakt(autorKontakt);

                autoriPrerade.add(autor);
            }
            deloPrerade.setAutori(autoriPrerade);
        }

        List<Autor> autoriDela = null;
        if(!dto.getAutorskoDelo().isAnonimanAutor()){
            autoriDela = new ArrayList<>();
            if(!dto.getAutorskoDelo().isPodnosilacJeAutor()){
                autoriDela = new ArrayList<>();
                for (AutorDto autorDto: dto.getAutorskoDelo().getAutoriDela()) {
                    Autor autor = new Autor();
                    autor.setIme(autorDto.getIme());
                    autor.setPrezime(autor.getPrezime());
                    autor.setDrzavljanstvo(autorDto.getDrzavljanstvo());
                    autor.setPseudonim(autorDto.getPseudonim());

                    Adresa autorAdresa = new Adresa();
                    autorAdresa.setUlica(autorDto.getAdresa().getUlica());
                    autorAdresa.setBroj(autorDto.getAdresa().getBroj());
                    autorAdresa.setPostanskiBroj(autorDto.getAdresa().getPostanskiBroj());
                    autorAdresa.setMesto(autorDto.getAdresa().getMesto());
                    autorAdresa.setDrzava(autorDto.getAdresa().getDrzava());

                    Kontakt autorKontakt = new Kontakt();
                    autorKontakt.setEmail(autorDto.getKontakt().getEmail());
                    autorKontakt.setTelefon(autorDto.getKontakt().getBroj());
                    autorKontakt.setFax(autorDto.getKontakt().getFax());
                    autor.setAdresa(autorAdresa);
                    autor.setKontakt(autorKontakt);

                    autoriDela.add(autor);
                }
            }
            else{
                autoriDela = new ArrayList<>();
                autoriDela.add(new Autor(podnosilac.getLice()));
            }


        }

        AutorskoDelo autorskoDelo = new AutorskoDelo();
        autorskoDelo.setNaslov(dto.getAutorskoDelo().getNaslovDela());
        autorskoDelo.setVrstaDela(dto.getAutorskoDelo().getVrstaDela());
        autorskoDelo.setFormaZapiseDela(dto.getAutorskoDelo().getFormaDela());
        autorskoDelo.setAnonimanAutor(dto.getAutorskoDelo().isAnonimanAutor());
        autorskoDelo.setNacinKoriscenja(dto.getAutorskoDelo().getNacinKoriscenjaDela());
        autorskoDelo.setStvorenoURadnomOdnosu(dto.getAutorskoDelo().isURadnomOdnosu());

        autorskoDelo.setDeloPrerade(deloPrerade);
        autorskoDelo.setAutori(autoriDela);

        Zahtev zahtev = new Zahtev();
        zahtev.setPrijava(prijava);
        zahtev.setZavod(zavod);
        zahtev.setPodnosilac(podnosilac);
        zahtev.setPunomocnik(punomocnik);
        zahtev.setAutorskoDelo(autorskoDelo);
        zahtev.setPrilozi(null);

        JAXBContext context = JAXBContext.newInstance(Zahtev.class);
        Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(zahtev, System.out);

        String newFilePath = "src/main/resources/xml/generated/" + zahtev.getPrijava().getBrojPrijave() + ".xml";
        File file = new File(newFilePath);
        m.marshal(zahtev, file);
        boolean valid = this.existService.validateXMLSchema("src/main/resources/xml/A-1.xsd", newFilePath);
        if(valid){
            String xmlData = Files.readString(Paths.get(newFilePath));
            this.repository.save(zahtev.getPrijava().getBrojPrijave() + "", xmlData, "/db/autorskoDelo/zahtevi");
            this.uploadZahtevMetadata(xmlData);
            return;
        }
        throw new FormatNotValidException();
    }

    private void uploadZahtevMetadata(String xmlData) throws IOException {
        String xsltFIlePath = "./src/main/resources/xml/metadata.xsl";
        String outputPath = "./src/main/resources/static/rdf.xml";

        metadataService.transformRDF(xmlData, xsltFIlePath, outputPath); // 1. xml u obliku string-a
        String resultMeta = metadataService.extractMetadataToRdf(new FileInputStream(new File("./src/main/resources/static/rdf.xml")), "./src/main/resources/static/extracted_rdf.xml");

        metadataService.uploadMetadata("./src/main/resources/static/extracted_rdf.xml", ""); ///graph/metadata/a1
    }

    public List<SearchResultDto> basicSearch(String text) {
        return this.metadataService.basicSearch(text, "./data/sparql/basicSearch.rq");
    }

    public String getRequestHTML(String brojPrijave) throws XMLDBException, IOException {
        XMLResource res = this.findZahtevById(brojPrijave);
        String xmlData = res.getContent().toString();

        transformer.transformToHtml(xmlData);
        return Files.readString(Paths.get("src/main/resources/xml/GeneratedHTML.html"));
    }

    public XMLResource findZahtevById(String resourceId) throws XMLDBException {
        return this.repository.findById(resourceId + ".xml", "/db/autorskoDelo/zahtevi");
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

    public String getMetadataJSON(String brojPrijave) throws XMLDBException, IOException {
        JSONObject json = XML.toJSONObject(getMetadataRDF(brojPrijave));
        return json.toString();
    }

    public String getMetadataRDF(String brojPrijave) throws XMLDBException, IOException {
        String xsltFIlePath = "./src/main/resources/xml/metadata.xsl";
        String outputPath = "./src/main/resources/static/rdf.xml";
        XMLResource res = this.repository.findById(brojPrijave + ".xml", "/db/autorskoDelo/zahtevi");

        this.metadataService.transformRDF(res.getContent().toString(), xsltFIlePath, outputPath); // 1. xml u obliku string-a
        String resultMetaFile = this.metadataService.extractMetadataToRdf(new FileInputStream(new File("./src/main/resources/static/rdf.xml")), "./src/main/resources/static/extracted_rdf.xml");
        return Files.readString(Path.of(resultMetaFile));
    }

    public void approveRequest(ResponseToPendingRequestDto dto) throws Exception {
        Resenje resenje = new Resenje(dto);
        resenje.setPrihvacena(true);

        JAXBContext context = JAXBContext.newInstance(Resenje.class);
        StringWriter stringWriter = new StringWriter();
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(resenje, stringWriter);

        this.repository.save(resenje.getBrojResenja(), stringWriter.toString(), "/db/patent/resenja");
        uploadConclusionMetadata(stringWriter.toString(), resenje.getBrojResenja());
        updateBrojResenjaInZahtev(dto.getBrojPrijave(), resenje.getBrojResenja());
    }

    private void uploadConclusionMetadata(String xmlData, String brojResenja) throws IOException {
        String xsltFIlePath = "./src/main/resources/xml/resenje-metadata.xsl";
        String outputPath = "./src/main/resources/static/rdf.xml";

        metadataService.transformRDF(xmlData, xsltFIlePath, outputPath); // 1. xml u obliku string-a
        String resultMeta = metadataService.extractMetadataToRdf(new FileInputStream(new File("./src/main/resources/static/rdf")), "./src/main/resources/static/extracted_rdf.xml");

        metadataService.uploadResenjeMetadata("./src/main/resources/static/extracted_rdf.xml", "");
    }

    private void updateBrojResenjaInZahtev(String brojPrijave, String brojResenja) throws Exception {
        XMLResource res = this.repository.findById(brojPrijave + ".xml", "/db/autorskoDelo/zahtevi");
        JAXBContext context = JAXBContext.newInstance(Zahtev.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(new StringReader((String) res.getContent()));
        Zahtev zahtev = (Zahtev) unmarshaller.unmarshal(reader);
        zahtev.setBrojResenja(brojResenja);

        StringWriter stringWriter = new StringWriter();
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(zahtev, stringWriter);

        this.repository.save(brojPrijave, stringWriter.toString(), "/db/autorskoDelo/zahtevi");

        this.metadataService.transformRDF(stringWriter.toString(), "./src/main/resources/xml/metadata.xsl", "./src/main/resources/static/rdf.xml");
        this.metadataService.extractMetadataToRdf(new FileInputStream(new File("./src/main/resources/static/rdf.xml")), "./src/main/resources/static/extracted_rdf.xml");
        this.metadataService.updateBrojResenjaMetaInZahtev("./src/main/resources/static/extracted_rdf.xml", stringWriter.toString(), brojPrijave, brojResenja);
        this.metadataService.uploadZahtevMetadata("");
    }




}
