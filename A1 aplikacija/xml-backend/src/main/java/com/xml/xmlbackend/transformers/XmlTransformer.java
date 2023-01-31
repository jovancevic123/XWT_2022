package com.xml.xmlbackend.transformers;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.xml.xmlbackend.model.a1.Zahtev;
import com.xml.xmlbackend.service.MetadataService;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URL;

public class XmlTransformer {
    private static Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try
        {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) );
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public static StringWriter transform(final String xmlString) {
        try{
            ClassLoader classloader = XmlTransformer.class.getClassLoader();
            URL xsltURL = classloader.getResource("xml/xslt.xsl");

            Document xmlDocument = convertStringToDocument(xmlString);
            Source stylesource = new StreamSource(xsltURL.openStream(), xsltURL.toExternalForm());
            Transformer transformer = TransformerFactory.newInstance().newTransformer(stylesource);

            StringWriter stringWriter = new StringWriter();
            StreamResult result = new StreamResult(stringWriter);
            transformer.transform(new DOMSource(xmlDocument), result);

            return stringWriter;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }



    public static void transformToPdf(final String xmlString) throws IOException {

        StringWriter stringWriter = transform(xmlString);

        ByteArrayInputStream is = new ByteArrayInputStream(stringWriter.toString().getBytes());
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter("src/main/resources/xml/GeneratedPDF.pdf"));
        pdfDocument.setDefaultPageSize(new PageSize(780, 3500));
        HtmlConverter.convertToPdf(is, pdfDocument);
    }

    public static void transformToHtml(final String xmlString) throws IOException {
        StringWriter stringWriter = transform(xmlString);

        File file = new File("src/main/resources/xml/GeneratedHTML.html");
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(stringWriter.toString());
        bw.close();
    }

    public static void main(String[] args) throws Exception {
//        transformToPdf("src/main/resources/xml/A-1-generated.xml");
//        transformToHtml("xml/A-1-generated.xml");
//
//        JAXBContext context = JAXBContext.newInstance(Zahtev.class);
//        Unmarshaller unmarshaller = context.createUnmarshaller();
//        Zahtev zahtev = (Zahtev) unmarshaller.unmarshal(new File("src/main/resources/xml/P-1-generated.xml"));
//
//        String rdfFile = "src/main/resources/xml/123.rdf";
//        service.generateRdf(rdfFile, zahtev);


// ------------------grddl
//        String xmlData = new String(Files.readAllBytes(Paths.get("./src/main/resources/xml/P-1-generated.xml")), StandardCharsets.UTF_8);
//        String xsltFIlePath = "./src/main/resources/xml/metadata.xsl";
//        String outputPath = "./src/main/resources/static/rdf/";
//        MetadataService service = new MetadataService();
//
//        service.transformRDF(xmlData, xsltFIlePath, outputPath); // 1. xml u obliku string-a
//        String resultMeta = service.extractMetadataToRdf(new FileInputStream(new File("./src/main/resources/static/rdf")), "123");
//
//        service.uploadMetadata();

//        ------------select metadata
//        MetadataService service = new MetadataService();
//        service.metaDataSelect(new SearchMetadataDto("999", "-1"));
    }
}
