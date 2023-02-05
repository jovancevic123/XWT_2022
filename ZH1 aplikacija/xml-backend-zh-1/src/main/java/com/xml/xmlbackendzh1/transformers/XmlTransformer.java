package com.xml.xmlbackendzh1.transformers;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.xml.xmlbackendzh1.dto.SearchMetadataDto;
import com.xml.xmlbackendzh1.service.MetadataService;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.xml.sax.InputSource;

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

@Service
public class XmlTransformer {

    public StringWriter transform(final String xmlString) {
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

    private Document convertStringToDocument(String xmlStr) {
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

    public void transformToHtml(final String xmlString) throws IOException {
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

    public void transformToPdf(final String xmlString) throws IOException {

        StringWriter stringWriter = transform(xmlString);

        ByteArrayInputStream is = new ByteArrayInputStream(stringWriter.toString().getBytes());
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter("src/main/resources/xml/GeneratedPDF.pdf"));
        pdfDocument.setDefaultPageSize(new PageSize(780, 2000));
        HtmlConverter.convertToPdf(is, pdfDocument);
    }

    public static void main(String[] args) throws Exception {
//        transformToPdf("xml/ZH-1-generated.xml", "xml/xslt.xsl");    //transform to pdf
//        transformToHtml("xml/ZH-1-generated.xml", "xml/xslt.xsl");   //transform to html

//        MetadataService service = new MetadataService();
//        service.transformRDF("xml/ZH-1-generated.xml", "xml/xslt.xsl", "static/");
//        service.metaDataSelect(new SearchMetadataDto("123"));       //extract metadata
    }
}
