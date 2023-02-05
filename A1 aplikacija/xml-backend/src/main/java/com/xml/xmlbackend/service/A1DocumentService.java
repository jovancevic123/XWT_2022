package com.xml.xmlbackend.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.xml.xmlbackend.dao.A1DocumentDAO;
import com.xml.xmlbackend.dto.*;
import com.xml.xmlbackend.exception.FormatNotValidException;
import com.xml.xmlbackend.exception.QueryFormatException;
import com.xml.xmlbackend.model.a1.Resenje;
import com.xml.xmlbackend.model.a1.*;
import com.xml.xmlbackend.transformers.XmlTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import org.json.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class A1DocumentService {

    private final ExistService existService;
    private final A1DocumentDAO repository;
    private final MetadataService metadataService;
    private final XmlTransformer transformer;
    private final EmailService emailService;

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
                autor.setPrezime(autorDto.getPrezime());
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
                    autor.setPrezime(autorDto.getPrezime());
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
        zahtev.setBrojResenja("");
        zahtev.setOpisDela("");
        zahtev.setPrimerDela("");

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

    public List<SearchResultDto> basicSearchForUser(String text, String email) {
        return this.metadataService.basicSearchForUser(text,email, "./data/sparql/basicSearchForUser.rq");
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

    public String approveRequest(ResponseToPendingRequestDto dto) throws Exception {
        Resenje resenje = new Resenje(dto);
        resenje.setPrihvacena(true);

        JAXBContext context = JAXBContext.newInstance(Resenje.class);
        StringWriter stringWriter = new StringWriter();
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(resenje, stringWriter);

        this.repository.save(resenje.getBrojResenja(), stringWriter.toString(), "/db/autorskoDelo/resenja");
        uploadConclusionMetadata(stringWriter.toString(), resenje.getBrojResenja());
        updateBrojResenjaInZahtev(dto.getBrojPrijave(), resenje);

        return resenje.getBrojResenja();
    }

    private void uploadConclusionMetadata(String xmlData, String brojResenja) throws IOException {
        String xsltFIlePath = "./src/main/resources/xml/resenje-metadata.xsl";
        String outputPath = "./src/main/resources/static/resenje-rdf.xml";
        String extractedRdfPath = "./src/main/resources/static/extracted_rdf.xml";

        metadataService.transformRDF(xmlData, xsltFIlePath, outputPath); // 1. xml u obliku string-a
        String resultMeta = metadataService.extractMetadataToRdf(new FileInputStream(new File(outputPath)), extractedRdfPath);

        metadataService.uploadResenjeMetadata(extractedRdfPath, "");
    }

    private void updateBrojResenjaInZahtev(String brojPrijave, Resenje resenje) throws Exception {
        String brojResenja = resenje.getBrojResenja();
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

        File file = generateResenjePdf(resenje);

        emailService.sendResenje(zahtev.getPodnosilac().getLice().getKontakt().getEmail(), file);
    }


    public Resenje findResenjeById(String resourceId) throws JAXBException, XMLDBException {
        return this.repository.findUnmarshalledResenjeById(resourceId);
    }

    public String rejectRequest(ResponseToPendingRequestDto dto) throws Exception {
        Resenje resenje = new Resenje(dto);
        resenje.setObrazlozenje(dto.getObrazlozenje());
        resenje.setPrihvacena(false);

        JAXBContext context = JAXBContext.newInstance(Resenje.class);
        StringWriter stringWriter = new StringWriter();
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(resenje, stringWriter);

        this.repository.save(resenje.getBrojResenja(), stringWriter.toString(), "/db/autorskoDelo/resenja");
        uploadConclusionMetadata(stringWriter.toString(), resenje.getBrojResenja());
        updateBrojResenjaInZahtev(dto.getBrojPrijave(), resenje);

        return resenje.getBrojResenja();
    }

    public List<SearchResultDto> advancedSearch(AdvancedSearchListDto list) throws JAXBException, XMLDBException {
        String pred = "http://www.ftn.uns.ac.rs/rdf/examples/predicate/";

        String queryString =
                "PREFIX schema: <http://www.ftn.uns.ac.rs/rdf/examples/predicate>\n" +
                        "prefix xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                        "SELECT * from <http://localhost:3030/AutorskoDeloDataset/data>\n" +
                        "WHERE {\n ?zahtev  <http://www.ftn.uns.ac.rs/rdf/examples/predicate/broj_prijave> ?brojPrijave . \n" ;

        for(AdvancedSearchDto dto : list.getConditions()){
            if(!dto.getMeta().equals("broj_prijave")) {
                if(dto.getMeta().equals("punomocnik_email"))
                    queryString = queryString.concat("OPTIONAL { \n");
                queryString = queryString.concat(String.format("?zahtev <%s%s> ?%s . \n", pred, dto.getMeta(), namingConversion(dto.getMeta())));
                if(dto.getMeta().equals("autor_email"))
                    queryString = queryString.concat("} \n");
            }
        }

        queryString = queryString.concat("FILTER( \n");

        int i = 0;
        for(AdvancedSearchDto dto : list.getConditions()){
            i++;
            queryString = queryString.concat(String.format("CONTAINS(UCASE(str(?%s)), UCASE('%s'))\n ", namingConversion(dto.getMeta()), dto.getValue()));
            if(i < list.getConditions().size()){
                queryString = queryString.concat(String.format("%s ", getQueryOperator(dto.getOperator())));
            }
        }

        queryString = queryString.concat(").\n}");
        System.out.println(queryString);
        return this.metadataService.advancedSearch(queryString);
    }

    public List<SearchResultDto> advancedSearchForUser(AdvancedSearchListDto list) throws JAXBException, XMLDBException {
        String pred = "http://www.ftn.uns.ac.rs/rdf/examples/predicate/";

        String queryString =
                "PREFIX schema: <http://www.ftn.uns.ac.rs/rdf/examples/predicate>\n" +
                        "prefix xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                        "SELECT * from <http://localhost:3030/AutorskoDeloDataset/data>\n" +
                        "WHERE {\n ?zahtev  <http://www.ftn.uns.ac.rs/rdf/examples/predicate/broj_prijave> ?brojPrijave . \n" ;

        for(AdvancedSearchDto dto : list.getConditions()){
            if(!dto.getMeta().equals("broj_prijave")) {
                if(dto.getMeta().equals("punomocnik_email"))
                    queryString = queryString.concat("OPTIONAL { \n");
                queryString = queryString.concat(String.format("?zahtev <%s%s> ?%s . \n", pred, dto.getMeta(), namingConversion(dto.getMeta())));
                if(dto.getMeta().equals("autor_email"))
                    queryString = queryString.concat("} \n");
            }
        }

        queryString = queryString.concat("FILTER( \n");

        int i = 0;
        for(AdvancedSearchDto dto : list.getConditions()){
            i++;
            queryString = queryString.concat(String.format("CONTAINS(UCASE(str(?%s)), UCASE('%s'))\n ", namingConversion(dto.getMeta()), dto.getValue()));
            if(i < list.getConditions().size()){
                queryString = queryString.concat(String.format("%s ", getQueryOperator(dto.getOperator())));
            }
            if(dto.getMeta().equals("podnosilac_email"))
                queryString = queryString.concat(" (");
        }

        queryString = queryString.concat(")).\n}");
        System.out.println(queryString);
        return this.metadataService.advancedSearch(queryString);
    }

    private Object namingConversion(String undercase) {
        switch (undercase){
            case "broj_prijave": return "brojPrijave";
            case "datum_podnosenja": return "datumPodnosenja";
            case "podnosilac_email": return "podnosilacEmail";
            case "punomocnik_email": return "punomocnikEmail";
            case "naslov_dela": return "naslovDela";
            case "broj_resenja": return "brojResenja";
            case "autor_email": return "autorEmail";
            case "": return "";
            default: throw new QueryFormatException("Malformed query!");
        }
    }

    private String getQueryOperator(String op){
        switch (op.toLowerCase()){
            case "and": case "i": return "&&";
            case "or": case "ili": return "||";
            case "not": case "ne": return "!=";
            case "":return "";
            default: throw new QueryFormatException("Malformed query!");
        }
    }

    public File generateResenjePdf(Resenje resenje) throws IOException, DocumentException {
        Document document = new Document();
        String path = "./src/main/resources/xml/resenja/" + new Date().getTime() + ".pdf";
        File file = null;

        try {
            file = new File(path);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();

        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("Rešenje zahteva: " + resenje.getBrojPrijave() + "\n\n\n\n\n", font);

        PdfPTable table = new PdfPTable(2);
        addTableHeader(table);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String prihvaceno = "Ne";
        String obrazlozenje = "";
        if(resenje.isPrihvacena())
            prihvaceno = "Da";
        else
            obrazlozenje = resenje.getObrazlozenje();


        addStringRows(table, "Broj resenja:", resenje.getBrojResenja());
        addStringRows(table, "Ime sluzbenika:", resenje.getImeSluzbenika());
        addStringRows(table, "Prezime sluzbenika:", resenje.getPrezimeSluzbenika());
        addStringRows(table, "Datum odgovora:", resenje.getDatumOdgovora().format(formatter));
        addStringRows(table, "Prihvaceno:", prihvaceno);
        addStringRows(table, "Obrazlozenje:", obrazlozenje);

        Paragraph paragraph1 = new Paragraph(chunk);
        document.add(paragraph1);

        document.add(table);

        document.close();

        return file;

//        byte[] fileContent = Files.readAllBytes(file.toPath());
//        ByteArrayResource body = new ByteArrayResource(fileContent);

    }

    public ByteArrayResource generateReport(String startDate, String endDate) throws IOException, DocumentException {
        Document document = new Document();
        String path = "./src/main/resources/xml/reports/" + new Date().getTime() + ".pdf";
        File file = null;

        try {
            file = new File(path);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();

        int brojPodnetihPrijava = this.metadataService.requestsThatAreReceivedBetween(startDate, endDate, "./data/sparql/reportRequestQuery.rq");
        int acceptedCounter = this.metadataService.numberOfResponsesBetween(startDate, endDate, "./data/sparql/reportRequestQuery2.rq");
        int rejectedCounter = this.metadataService.numberOfResponsesBetween(startDate, endDate, "./data/sparql/reportRequestQuery3.rq");

        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("Izveštaj za period: " + startDate + " --- " + endDate + "\n\n\n\n\n", font);

        PdfPTable table = new PdfPTable(2);
        addTableHeader(table);

        addRows(table, "Broj podnetih zahteva:", brojPodnetihPrijava);
        addRows(table, "Broj prihvacenih zahteva:", acceptedCounter);
        addRows(table, "Broj odbijenih zahteva:", rejectedCounter);

        Paragraph paragraph1 = new Paragraph(chunk);
        document.add(paragraph1);

        document.add(table);

        document.close();

        byte[] fileContent = Files.readAllBytes(file.toPath());
        ByteArrayResource body = new ByteArrayResource(fileContent);
        file.delete();
        return body;
    }

    private void addRows(PdfPTable table, String text, int counter) {
        table.addCell(text);
        table.addCell(counter + "");
    }

    private void addStringRows(PdfPTable table, String text, String addition) {
        table.addCell(text);
        table.addCell(addition + "");
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Predmet", "Ukupan broj")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }
}
