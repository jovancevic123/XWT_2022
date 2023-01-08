package com.xml.backend.p1.transformers;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

import com.xml.backend.p1.exceptions.OperationFailedException;
import com.xml.backend.p1.model.Zahtev;
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

    public static final String XSL_TO_RDF_FILE = "xml/metadata.xsl";

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

        File file = new File("src/main/resources/xml/HTML.html");
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
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter("./joco.pdf"));
        pdfDocument.setDefaultPageSize(new PageSize(780, 2000));
        HtmlConverter.convertToPdf(is, pdfDocument);
    }

    public static void generateRdf(String rdfFile, Zahtev zahtev){
        try {
            FusekiAuthentication.ConnectionProperties conn = FusekiAuthentication.loadProperties();

            TransformerFactory factory = TransformerFactory.newInstance();
            ClassLoader classloader = XmlTransformer.class.getClassLoader();
            InputStream xmlData = classloader.getResourceAsStream(XSL_TO_RDF_FILE);

            StreamSource xslt = new StreamSource(xmlData);
            Transformer transformer = factory.newTransformer(xslt);

            JAXBContext context = JAXBContext.newInstance(Zahtev.class);
            JAXBSource source = new JAXBSource(context, zahtev);
            System.out.println("Source: " + source.getXMLReader().toString());
            StreamResult result = new StreamResult(new FileOutputStream(rdfFile));

            transformer.transform(source, result);

//             Creates a default model
            Model model = ModelFactory.createDefaultModel();
            int brojPrijave = zahtev.getPrijava().getBrojPrijave();
            String RDF_FILE = "src/main/resources/xml/" + brojPrijave + ".rdf";
            model.read(RDF_FILE);

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            model.write(out, "N-TRIPLES");

            System.out.println("[INFO] Rendering model as RDF/XML...");
            model.write(System.out, SparqlUtil.RDF_XML);

//             Creating the first named graph and updating it with RDF data
            System.out.println("[INFO] Writing the triples to a named graph \"" + brojPrijave + "\".");
            String sparqlUpdate = insertData(conn.dataEndpoint + brojPrijave, new String(out.toByteArray()));
            System.out.println(sparqlUpdate);

            // UpdateRequest represents a unit of execution
            UpdateRequest update = UpdateFactory.create(sparqlUpdate);

            UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, conn.updateEndpoint);
            try{
//                processor.execute();
                System.out.println("[INFO] Selecting the triples from the named graph \"" + "\".");
                String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint, "?s ?p ?o");
                QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

                // Query the SPARQL endpoint, iterate over the result set...
                ResultSet results = query.execSelect();

                String varName;
                RDFNode varValue;

                while(results.hasNext()) {

                    // A single answer from a SELECT query
                    QuerySolution querySolution = results.next() ;
                    Iterator<String> variableBindings = querySolution.varNames();

                    // Retrieve variable bindings
                    while (variableBindings.hasNext()) {

                        varName = variableBindings.next();
                        varValue = querySolution.get(varName);

                        System.out.println(varName + ": " + varValue);
                    }
                    System.out.println();
                }


            }catch(Exception ex){
                ex.printStackTrace();
            }
//            deleteFile(rdfFile);

        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    public static String insertData(String graphURI, String ntriples) {
        return String.format("INSERT DATA { GRAPH <%1$s> { %2$s } }", graphURI, ntriples);
    }

    public static String selectData(String graphURI, String sparqlCondition) {
        return String.format("SELECT * FROM <%1$s> WHERE { %2$s }", graphURI, sparqlCondition);
    }

    public static void main(String[] args) throws Exception {
//        transformToPdf("xml/P-1-generated.xml", "xml/xslt.xsl");
//        transformToHtml("xml/P-1-generated.xml", "xml/xslt.xsl");
//
        JAXBContext context = JAXBContext.newInstance(Zahtev.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Zahtev zahtev = (Zahtev) unmarshaller.unmarshal(new File("src/main/resources/xml/P-1-generated.xml"));

        String rdfFile = "src/main/resources/xml/123.rdf";
        generateRdf(rdfFile, zahtev);


// ------------------grddl
//        String xmlData = new String(Files.readAllBytes(Paths.get("./src/main/resources/xml/P-1-generated.xml")), StandardCharsets.UTF_8);
//        String xsltFIlePath = "./src/main/resources/xml/metadata1.xsl";
//        String outputPath = "./src/main/resources/static/rdf/";
//
//
//        transformRDF(xmlData, xsltFIlePath, outputPath); // 1. xml u obliku string-a
//        String resultMeta = extractMetadataToRdf(new FileInputStream(new File("./src/main/resources/static/rdf")), "123");
//        uploadMetadata();
    }

    public static void uploadMetadata() throws IOException {
        FusekiAuthentication.ConnectionProperties conn = FusekiAuthentication.loadProperties();

        // Creates a default model
        Model model = ModelFactory.createDefaultModel();
        String filePath = "./src/main/resources/static/grddl" + "123" + ".xml";
        model.read(filePath);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        model.write(out, SparqlUtil.NTRIPLES);

        model.write(System.out, SparqlUtil.RDF_XML);

        UpdateRequest request = UpdateFactory.create() ;

        UpdateProcessor processor = UpdateExecutionFactory.createRemote(request, conn.updateEndpoint);
        processor.execute();

        // Creating the first named graph and updating it with RDF data
        String sparqlUpdate = insertData(conn.dataEndpoint + "/patenti/metadata", new String(out.toByteArray()));

        // UpdateRequest represents a unit of execution
        UpdateRequest update = UpdateFactory.create(sparqlUpdate);

        processor = UpdateExecutionFactory.createRemote(update, conn.updateEndpoint);
        processor.execute();
    }

    public static String extractMetadataToRdf(InputStream in, String id) {
        StreamSource transformSource = new StreamSource(new File("./data/grddl/grddl.xsl"));
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer grddlTransformer = null;

        try {
            grddlTransformer = factory.newTransformer(transformSource);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            throw new OperationFailedException("Failed to initialize GRDDL transformer");
        }

        grddlTransformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
        grddlTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

        String outputFilePath = "./src/main/resources/static/grddl" + id + ".xml";

        //StreamSource source = new StreamSource(new ByteArrayInputStream(xmlData.getBytes(StandardCharsets.UTF_8)));
        StreamSource source = new StreamSource(in);
        StreamResult result = null;

        try {
            result = new StreamResult(new FileOutputStream(outputFilePath));
        } catch (FileNotFoundException e) {
            throw new OperationFailedException("Error while creating output file");
        }

        try {
            grddlTransformer.transform(source, result);
        } catch (TransformerException e) {
            throw new OperationFailedException("Error while extracting metadata to RDF");
        }

        String rdfXmlResult = readFromFile(outputFilePath);
        //File outputFile = new File(outputFilePath);
        //outputFile.delete();

        return outputFilePath;
    }

    public static void transformRDF(String xmlData, String xsltFIlePath, String outputPath) {
        String path = "./src/main/resources/static/data.xml";
        try{
            FileUtils.writeStringToFile(new File(path), xmlData);
            StreamSource streamSource = new StreamSource(new File(xsltFIlePath));
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = null;

            try {
                transformer = factory.newTransformer(streamSource);
            } catch (TransformerConfigurationException e) {
                throw new OperationFailedException("Error while creating XSLT transformer object.");
            }

            transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");

            DOMSource xmlSource = new DOMSource(buildDocument(path));
            StreamResult result = new StreamResult(new FileOutputStream(outputPath));

            try{
                transformer.transform(xmlSource, result);
            }catch(TransformerException e){
                throw new OperationFailedException("XSLT error while transforming the document with " + xsltFIlePath);
            }

        }catch (IOException e){
        }
    }

    public static String readFromFile(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new OperationFailedException("Error while reading data from the file: " + filePath);
        }

        return contentBuilder.toString();
    }

    private static org.w3c.dom.Document buildDocument(String filePath) {

        org.w3c.dom.Document document = null;
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        documentFactory.setNamespaceAware(true);
        documentFactory.setIgnoringComments(true);
        documentFactory.setIgnoringElementContentWhitespace(true);
        try {

            DocumentBuilder builder = documentFactory.newDocumentBuilder();
            document = builder.parse(new File(filePath));

            if (document != null)
                System.out.println("[INFO] File parsed with no errors.");
            else
                System.out.println("[WARN] Document is null.");

        } catch (Exception e) {
            return null;
        }

        return document;
    }
}
