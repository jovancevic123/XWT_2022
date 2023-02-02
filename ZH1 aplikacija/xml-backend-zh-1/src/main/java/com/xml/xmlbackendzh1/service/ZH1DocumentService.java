package com.xml.xmlbackendzh1.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.xml.xmlbackendzh1.dao.ExistDao;
import com.xml.xmlbackendzh1.dto.*;
import com.xml.xmlbackendzh1.exceptions.FormatNotValidException;
import com.xml.xmlbackendzh1.exceptions.QueryFormatException;
import com.xml.xmlbackendzh1.model.zh1.*;
import com.xml.xmlbackendzh1.transformers.XmlTransformer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
@Getter
@Setter
public class ZH1DocumentService {

    private final ExistDao repository;
    private final ExistService existService;
    private final MetadataService metadataService;
    private final XmlTransformer transformer;

    @Autowired
    public ZH1DocumentService(ExistDao repository, ExistService existService, MetadataService metadataService, XmlTransformer transformer) {
        this.repository = repository;
        this.existService = existService;
        this.metadataService = metadataService;
        this.transformer = transformer;
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

        this.repository.save(resenje.getBrojResenja(), stringWriter.toString(), "/db/zig/resenja");
        uploadResenjeMetadata(stringWriter.toString(), resenje.getBrojResenja());
        updateBrojResenjaInZahtev(dto.getBrojPrijaveZiga(), resenje.getBrojResenja());
    }

    private Resenje makeResenjeFromDto(ResponseToPendingRequestDto dto){
        long brojResenja = (long) new Date().getTime();
        Resenje resenje = new Resenje();

        resenje.setBrojPrijaveZiga(dto.getBrojPrijaveZiga());
        resenje.setDatumOdgovora(LocalDate.now());
        resenje.setBrojResenja(Long.toString(brojResenja));
        resenje.setImeSluzbenika(dto.getImeSluzbenika().split(" ")[0]);
        resenje.setPrezimeSluzbenika(dto.getImeSluzbenika().split(" ")[1]);

        return resenje;
    }

    private void uploadResenjeMetadata(String xmlData, String brojResenja) throws IOException {
        String xsltFIlePath = "./src/main/resources/xml/resenje-metadata.xsl";
        String outputPath = "./src/main/resources/static/rdf/";

        metadataService.transformRDF(xmlData, xsltFIlePath, outputPath); // 1. xml u obliku string-a
        String resultMeta = metadataService.extractMetadataToRdf(new FileInputStream(new File("./src/main/resources/static/rdf")), "./src/main/resources/static/extracted_rdf.xml");

        metadataService.uploadResenjeMetadata("./src/main/resources/static/extracted_rdf.xml", "/graph/metadata/zh1");
    }

    private void updateBrojResenjaInZahtev(String brojPrijave, String brojResenja) throws Exception {
        XMLResource res = this.repository.findById(brojPrijave + ".xml", "/db/zig/zahtevi");
        JAXBContext context = JAXBContext.newInstance(Zahtev.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(new StringReader((String) res.getContent()));
        Zahtev zahtev = (Zahtev) unmarshaller.unmarshal(reader);
        zahtev.setBrojResenja(brojResenja);

        StringWriter stringWriter = new StringWriter();
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(zahtev, stringWriter);

        this.repository.save(brojPrijave, stringWriter.toString(), "/db/zig/zahtevi");

        this.metadataService.transformRDF(stringWriter.toString(), "./src/main/resources/xml/metadata.xsl", "./src/main/resources/static/rdf/");
        this.metadataService.extractMetadataToRdf(new FileInputStream(new File("./src/main/resources/static/rdf")), "./src/main/resources/static/extracted_rdf.xml");
        this.metadataService.updateBrojResenjaMetaInZahtev("./src/main/resources/static/extracted_rdf.xml", stringWriter.toString(), brojPrijave, brojResenja);
        this.metadataService.uploadZahtevMetadata("/graph/metadata/zh1");
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

        this.repository.save(resenje.getBrojResenja(), stringWriter.toString(), "/db/zig/resenja");
        uploadResenjeMetadata(stringWriter.toString(), resenje.getBrojResenja());
        updateBrojResenjaInZahtev(dto.getBrojPrijaveZiga(), resenje.getBrojResenja());
    }

    public Resenje findResenjeById(String resourceId) throws XMLDBException, JAXBException {
        return this.repository.findUnmarshalledResenjeById(resourceId);
    }

    public ByteArrayResource getRequestPDF(String brojPrijaveZiga) throws XMLDBException, IOException {
        XMLResource res = this.findZahtevById(brojPrijaveZiga);
        String xmlData = res.getContent().toString();

        transformer.transformToPdf(xmlData);
        File f = new File("src/main/resources/xml/GeneratedPDF.pdf");
        byte[] fileContent = Files.readAllBytes(f.toPath());
        ByteArrayResource body = new ByteArrayResource(fileContent);
        return body;
    }

    public String getRequestHTML(String brojPrijaveZiga) throws XMLDBException, IOException {
        XMLResource res = this.findZahtevById(brojPrijaveZiga);
        String xmlData = res.getContent().toString();

        transformer.transformToHtml(xmlData);
        String html = Files.readString(Paths.get("src/main/resources/xml/GeneratedHTML.html"));
        return html;
    }

    public String getMetadataRDF(String brojPrijave) throws XMLDBException, IOException {
        String xsltFIlePath = "./src/main/resources/xml/metadata.xsl";
        String outputPath = "./src/main/resources/static/rdf/";
        XMLResource res = this.repository.findById(brojPrijave + ".xml", "/db/zig/zahtevi");

        this.metadataService.transformRDF(res.getContent().toString(), xsltFIlePath, outputPath); // 1. xml u obliku string-a
        String resultMetaFile = this.metadataService.extractMetadataToRdf(new FileInputStream(new File("./src/main/resources/static/rdf")), "./src/main/resources/static/extracted_rdf.xml");
        return Files.readString(Path.of(resultMetaFile));
    }

    public String getMetadataJSON(String brojPrijave) throws XMLDBException, IOException {
        JSONObject json = XML.toJSONObject(getMetadataRDF(brojPrijave));
        return json.toString();
    }

    public List<SearchResultsDto> basicSearch(String text) throws JAXBException, XMLDBException {
        return this.metadataService.basicSearch(text, "./data/sparql/basicSearch.rq");
    }

    public List<SearchResultsDto> advancedSearch(AdvancedSearchListDto list) throws JAXBException, XMLDBException {
        String pred = "http://www.ftn.uns.ac.rs/rdf/examples/predicate/";

        String queryString =
                "PREFIX schema: <http://www.ftn.uns.ac.rs/rdf/examples/predicate>\n" +
                        "prefix xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                        "SELECT * from <http://localhost:3030/zigovi/data/graph/metadata/zh1>\n" +
                        "WHERE {\n ?zig  <http://www.ftn.uns.ac.rs/rdf/examples/predicate/broj_prijave_ziga> ?brojPrijaveZiga . \n" ;

        for(AdvancedSearchDto dto : list.getConditions()){
            if(!dto.getMeta().equals("broj_prijave_ziga"))
                queryString = queryString.concat(String.format("?zig <%s%s> ?%s . \n", pred, dto.getMeta(), namingConversion(dto.getMeta())));
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
            case "broj_prijave_ziga": return "brojPrijaveZiga";
            case "broj_resenja": return "brojResenja";
            case "podnosilac_email": return "podnosilacEmail";
            case "punomocnik_email": return "punomocnikEmail";
            case "datum_podnosenja": return "datumPodnosenja";
            default: throw new QueryFormatException("Malformed query!");
        }
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

        int brojPodnetihPrijava = this.metadataService.requestsThatAreReceivedBetween(startDate, endDate, "./data/sparql/reportRequestQuery1.rq");
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
}

