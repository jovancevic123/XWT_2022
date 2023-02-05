package com.xml.backend.p1.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.xml.backend.p1.dao.ExistDao;
import com.xml.backend.p1.dto.*;
import com.xml.backend.p1.exceptions.FormatNotValidException;
import com.xml.backend.p1.exceptions.QueryFormatException;
import com.xml.backend.p1.model.*;
import com.xml.backend.p1.transformers.XmlTransformer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
@Getter
@Setter
public class P1DocumentService {

    private final ExistDao repository;
    private final XmlTransformer transformer;
    private final MetadataService metadataService;
    private final ExistService existService;

    @Autowired
    public P1DocumentService(ExistDao repository, XmlTransformer transformer,
                             MetadataService resenjeMetadataService, ExistService existService) {
        this.repository = repository;
        this.transformer = transformer;
        this.metadataService = resenjeMetadataService;
        this.existService = existService;
    }

    public XMLResource findZahtevById(String resourceId) throws XMLDBException {
        return this.repository.findById(resourceId + ".xml", "/db/patent/zahtevi");
    }

    public Resenje findResenjeById(String resourceId) throws XMLDBException, JAXBException {
        return this.repository.findUnmarshalledResenjeById(resourceId);
    }

    public void addPatent(RequestDto dto) throws Exception {
        int brojPrijave = (int) new Date().getTime();
        Prijava prijava = new Prijava(brojPrijave, LocalDate.now(), null, dto.getVrstaPrijave() == 1 ? "dopunska" : "izdvojena");
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
            punomocLice = new FizickoLice(new Adresa(dto.getPunomocAdresa()), new Kontakt(dto.getPunomocKontakt()), dto.getImePunomoc(), dto.getPrezimePunomoc());
        }else{
            punomocLice = new PravnoLice(new Adresa(dto.getPunomocAdresa()), new Kontakt(dto.getPunomocKontakt()), dto.getPoslovnoImePunomoc());
        }

        String nacinDostavljanja = dto.getElektronskaDostava() == 1 ? "elektronska_forma" : "papirna_forma";
        String tipPunomocnika = dto.getTipPunomocnika() == 1 ? "punomocnik_za_zastupanje" : "punomocnik_za_prijem_pismena";

        Podnosilac podnosilac = new Podnosilac(podnosilacLice, dto.isPodnosilacJePronalazac(), dto.getDrzavljanstvoPodnosilac());
        Pronalazac pronalazac = new Pronalazac(pronalazacLice, dto.isNavedenUPrijavi());
        Punomoc punomoc = new Punomoc(punomocLice, tipPunomocnika);

        List<RanijaPrijava> ranijePrijave = new ArrayList<>();
        if(dto.getRanijePrijave() != null){
            for(RanijaPrijavaDto rp : dto.getRanijePrijave()){
                ranijePrijave.add(new RanijaPrijava(rp));
            }
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
//        zahtev.setBrojResenja("");

        JAXBContext context = JAXBContext.newInstance(Zahtev.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//		m.marshal(zahtev, System.out);

        String newFilePath = "src/main/resources/xml/generated/" + zahtev.getPrijava().getBrojPrijave() + ".xml";
        File file = new File(newFilePath);
        m.marshal(zahtev, file);
        boolean valid = this.existService.validateXMLSchema("src/main/resources/xml/P-1.xsd", newFilePath);
        if(valid){
            String xmlData = Files.readString(Paths.get(newFilePath));
            this.repository.save(zahtev.getPrijava().getBrojPrijave() + "", xmlData, "/db/patent/zahtevi");
            this.uploadZahtevMetadata(xmlData);
            return;
        }
        throw new FormatNotValidException();
    }

    public void addPatentXonomy(Zahtev zahtev) throws Exception {
        int brojPrijave = (int) new Date().getTime();
        zahtev.getPrijava().setBrojPrijave(brojPrijave);
        zahtev.getPrijava().setDatumPrijema(LocalDate.now());
        Zavod zavod = new Zavod();
        zahtev.setZavod(zavod);


        JAXBContext context = JAXBContext.newInstance(Zahtev.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//        m.marshal(zahtev, System.out);
        String newFilePath = "src/main/resources/xml/generated/" + zahtev.getPrijava().getBrojPrijave() + ".xml";
        File file = new File(newFilePath);
        m.marshal(zahtev, file);
        boolean valid = this.existService.validateXMLSchema("src/main/resources/xml/P-1.xsd", newFilePath);
        if(valid){
            String xmlData = Files.readString(Paths.get(newFilePath));
            this.repository.save(zahtev.getPrijava().getBrojPrijave() + "", xmlData, "/db/patent/zahtevi");
            this.uploadZahtevMetadata(xmlData);
            return;
        }
        throw new FormatNotValidException();
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

    public List<SearchResultsDto> getPendingRequests() {
        return this.metadataService.getPendingRequests("./data/sparql/pendingRequests.rq");
    }

    public void approveRequest(ResponseToPendingRequestDto dto) throws Exception {
        Resenje resenje = makeResenjeFromDto(dto);
        resenje.setPrihvacena(true);

        JAXBContext context = JAXBContext.newInstance(Resenje.class);
        StringWriter stringWriter = new StringWriter();
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(resenje, stringWriter);

        this.repository.save(resenje.getBrojResenja(), stringWriter.toString(), "/db/patent/resenja");
        uploadResenjeMetadata(stringWriter.toString(), resenje.getBrojResenja());
        updateBrojResenjaInZahtev(dto.getBrojPrijave(), resenje.getBrojResenja());
    }

    public void rejectRequest(ResponseToPendingRequestDto dto) throws Exception {
        Resenje resenje = makeResenjeFromDto(dto);
        resenje.setObrazlozenje(dto.getObrazlozenje());
        resenje.setPrihvacena(false);

        JAXBContext context = JAXBContext.newInstance(Resenje.class);
        StringWriter stringWriter = new StringWriter();
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(resenje, stringWriter);

        this.repository.save(resenje.getBrojResenja(), stringWriter.toString(), "/db/patent/resenja");
        uploadResenjeMetadata(stringWriter.toString(), resenje.getBrojResenja());
        updateBrojResenjaInZahtev(dto.getBrojPrijave(), resenje.getBrojResenja());
    }

    private void updateBrojResenjaInZahtev(String brojPrijave, String brojResenja) throws Exception {
        XMLResource res = this.repository.findById(brojPrijave + ".xml", "/db/patent/zahtevi");
        JAXBContext context = JAXBContext.newInstance(Zahtev.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(new StringReader((String) res.getContent()));
        Zahtev zahtev = (Zahtev) unmarshaller.unmarshal(reader);
        zahtev.setBrojResenja(brojResenja);
        zahtev.getPrijava().setPriznatiDatumPodnosenja(LocalDate.now());
        StringWriter stringWriter = new StringWriter();
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(zahtev, stringWriter);

        this.repository.save(brojPrijave, stringWriter.toString(), "/db/patent/zahtevi");

        this.metadataService.transformRDF(stringWriter.toString(), "./src/main/resources/xml/metadata.xsl", "./src/main/resources/static/rdf/");
        this.metadataService.extractMetadataToRdf(new FileInputStream(new File("./src/main/resources/static/rdf")), "./src/main/resources/static/extracted_rdf.xml");
        this.metadataService.updateBrojResenjaMetaInZahtev("./src/main/resources/static/extracted_rdf.xml", stringWriter.toString(), brojPrijave, brojResenja);
        this.metadataService.uploadZahtevMetadata("/graph/metadata/p1");
    }

    private void uploadResenjeMetadata(String xmlData, String brojResenja) throws IOException {
        String xsltFIlePath = "./src/main/resources/xml/resenje-metadata.xsl";
        String outputPath = "./src/main/resources/static/rdf/";

        metadataService.transformRDF(xmlData, xsltFIlePath, outputPath); // 1. xml u obliku string-a
        String resultMeta = metadataService.extractMetadataToRdf(new FileInputStream(new File("./src/main/resources/static/rdf")), "./src/main/resources/static/extracted_rdf.xml");

        metadataService.uploadResenjeMetadata("./src/main/resources/static/extracted_rdf.xml", "/graph/metadata/p1");
    }

    private void uploadZahtevMetadata(String xmlData) throws IOException {
        String xsltFIlePath = "./src/main/resources/xml/metadata.xsl";
        String outputPath = "./src/main/resources/static/rdf/";

        metadataService.transformRDF(xmlData, xsltFIlePath, outputPath); // 1. xml u obliku string-a
        String resultMeta = metadataService.extractMetadataToRdf(new FileInputStream(new File("./src/main/resources/static/rdf")), "./src/main/resources/static/extracted_rdf.xml");

        metadataService.uploadResenjeMetadata("./src/main/resources/static/extracted_rdf.xml", "/graph/metadata/p1");
    }

    private Resenje makeResenjeFromDto(ResponseToPendingRequestDto dto){
        long brojResenja = (long) new Date().getTime();
        Resenje resenje = new Resenje();

        resenje.setBrojPrijave(dto.getBrojPrijave());
        resenje.setDatumOdgovora(LocalDate.now());
        resenje.setBrojResenja(Long.toString(brojResenja));
        resenje.setImeSluzbenika(dto.getImeSluzbenika().split(" ")[0]);
        resenje.setPrezimeSluzbenika(dto.getImeSluzbenika().split(" ")[1]);

        return resenje;
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

    private void addRows(PdfPTable table, String text, int counter) {
        table.addCell(text);
        table.addCell(counter + "");
    }

    public String getMetadataRDF(String brojPrijave) throws XMLDBException, IOException {
        String xsltFIlePath = "./src/main/resources/xml/metadata.xsl";
		String outputPath = "./src/main/resources/static/rdf/";
        XMLResource res = this.repository.findById(brojPrijave + ".xml", "/db/patent/zahtevi");

        this.metadataService.transformRDF(res.getContent().toString(), xsltFIlePath, outputPath); // 1. xml u obliku string-a
        String resultMetaFile = this.metadataService.extractMetadataToRdf(new FileInputStream(new File("./src/main/resources/static/rdf")), "./src/main/resources/static/extracted_rdf.xml");
        return Files.readString(Path.of(resultMetaFile));
    }

    public String getMetadataJSON(String brojPrijave) throws XMLDBException, IOException {
        JSONObject json = XML.toJSONObject(getMetadataRDF(brojPrijave));
        return json.toString();
    }

    public List<SearchResultsDto> basicSearchUser(String text, String email) {
        return this.metadataService.basicSearchUser(text, "./data/sparql/basicSearchUser.rq", email);
    }

    public List<SearchResultsDto> basicSearch(String text) throws JAXBException, XMLDBException {
        return this.metadataService.basicSearch(text, "./data/sparql/basicSearch.rq");
    }

    public List<SearchResultsDto> getUsersRequests(String email) throws JAXBException, XMLDBException {
        return this.metadataService.getUsersRequests(email, "./data/sparql/userRequests.rq");
    }

    public List<SearchResultsDto> advancedSearch(AdvancedSearchListDto list) throws JAXBException, XMLDBException {
        String pred = "http://www.ftn.uns.ac.rs/rdf/examples/predicate/";

        String queryString =
                "PREFIX schema: <http://www.ftn.uns.ac.rs/rdf/examples/predicate>\n" +
                "prefix xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "SELECT * from <http://localhost:3030/PatentDataset/data/graph/metadata/p1>\n" +
                "WHERE {\n ?patent  <http://www.ftn.uns.ac.rs/rdf/examples/predicate/broj_prijave> ?brojPrijave . \n" ;

        for(AdvancedSearchDto dto : list.getConditions()){
            if(!dto.getMeta().equals("broj_prijave"))
                queryString = queryString.concat(String.format("?patent <%s%s> ?%s . \n", pred, dto.getMeta(), namingConversion(dto.getMeta())));
        }

        queryString = queryString.concat("FILTER( \n");

        int i = 0;
        for(AdvancedSearchDto dto : list.getConditions()){
            i++;
            queryString = queryString.concat(String.format("CONTAINS(UCASE(str(?%s)), UCASE('%s'))\n ", namingConversion(dto.getMeta()), dto.getValue()));
            if(i < list.getConditions().size()){
                queryString = queryString.concat(String.format("%s ", dto.getOperator()));
            }
        }

        queryString = queryString.concat(").\n}");
        System.out.println(queryString);
        return this.metadataService.advancedSearch(queryString);
    }

    private String namingConversion(String undercase){
        switch (undercase){
            case "broj_prijave": return "brojPrijave";
            case "broj_resenja": return "brojResenja";
            case "naziv_srb": return "nazivSRB";
            case "naziv_eng": return "nazivENG";
            case "pronalazac_email": return "pronalazacEmail";
            case "podnosilac_email": return "podnosilacEmail";
            case "punomoc_email": return "punomocEmail";
            case "datum_prijema": return "datumPrijema";
            default: throw new QueryFormatException("Malformed query!");
        }
    }
}
