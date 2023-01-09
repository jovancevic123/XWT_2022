package com.xml.backend.p1.transformers;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

import com.xml.backend.p1.dto.SearchMetadataDto;
import com.xml.backend.p1.exceptions.OperationFailedException;
import com.xml.backend.p1.model.Zahtev;
import com.xml.backend.p1.service.MetadataService;
import com.xml.backend.p1.util.FusekiAuthentication;
import com.xml.backend.p1.util.SparqlUtil;
import org.apache.commons.io.FileUtils;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Stream;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.w3c.dom.Document;

public class XmlTransformer {

    public static StringWriter transform(final String xml, final String xslt) {
        try{
            ClassLoader classloader = XmlTransformer.class.getClassLoader();
            InputStream xmlData = classloader.getResourceAsStream(xml);
            URL xsltURL = classloader.getResource(xslt);

            Document xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlData);
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

    public static void transformToHtml(final String xml, final String xslt) throws IOException {
        StringWriter stringWriter = transform(xml, xslt);

        File file = new File("src/main/resources/xml/GeneratedHTML.html");
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(stringWriter.toString());
        bw.close();
    }

    public static void transformToPdf(final String xml, final String xslt) throws IOException {

       StringWriter stringWriter = transform(xml, xslt);

        ByteArrayInputStream is=new ByteArrayInputStream(stringWriter.toString().getBytes());
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter("src/main/resources/xml/GeneratedPDF.pdf"));
        pdfDocument.setDefaultPageSize(new PageSize(780, 2000));
        HtmlConverter.convertToPdf(is, pdfDocument);
    }

    public static void main(String[] args) throws Exception {
        transformToPdf("xml/P-1-generated.xml", "xml/xslt.xsl");
        transformToHtml("xml/P-1-generated.xml", "xml/xslt.xsl");
//
//        JAXBContext context = JAXBContext.newInstance(Zahtev.class);
//        Unmarshaller unmarshaller = context.createUnmarshaller();
//        Zahtev zahtev = (Zahtev) unmarshaller.unmarshal(new File("src/main/resources/xml/P-1-generated.xml"));
//
//        String rdfFile = "src/main/resources/xml/123.rdf";
//        generateRdf(rdfFile, zahtev);


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
        MetadataService service = new MetadataService();
        service.metaDataSelect(new SearchMetadataDto("123"));
    }
}
