package com.xml.xmlbackendzh1.transformers;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.xml.xmlbackendzh1.dto.SearchMetadataDto;
import com.xml.xmlbackendzh1.service.MetadataService;
import org.w3c.dom.Document;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
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
        transformToPdf("xml/ZH-1-generated.xml", "xml/xslt.xsl");    //transform to pdf
        transformToHtml("xml/ZH-1-generated.xml", "xml/xslt.xsl");   //transform to html

//        MetadataService service = new MetadataService();
//        service.metaDataSelect(new SearchMetadataDto("123"));       //extract metadata
    }
}
