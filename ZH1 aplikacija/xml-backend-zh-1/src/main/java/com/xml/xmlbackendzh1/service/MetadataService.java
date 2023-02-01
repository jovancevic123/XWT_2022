package com.xml.xmlbackendzh1.service;

import com.xml.xmlbackendzh1.dto.SearchMetadataDto;
import com.xml.xmlbackendzh1.dto.SearchResultsDto;
import com.xml.xmlbackendzh1.exceptions.OperationFailedException;
import com.xml.xmlbackendzh1.util.FusekiAuthentication;
import com.xml.xmlbackendzh1.util.FusekiAuthentication;
import com.xml.xmlbackendzh1.util.FusekiAuthentication.ConnectionProperties;
import com.xml.xmlbackendzh1.util.SparqlUtil;
import org.apache.commons.io.FileUtils;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.stereotype.Service;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.http.HttpClient;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

@Service
public class MetadataService {


    public final String XSL_TO_RDF_FILE = "xml/metadata.xsl";
    private ConnectionProperties connectionProperties;

    public MetadataService() throws IOException {
        connectionProperties = FusekiAuthentication.loadProperties();
    }

    public void uploadMetadata() throws IOException {
        FusekiAuthentication.ConnectionProperties conn = FusekiAuthentication.loadProperties();

        // Creates a default model
        Model model = ModelFactory.createDefaultModel();
        String filePath = "./src/main/resources/static/extracted_rdf.xml";
        model.read(filePath);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        model.write(out, SparqlUtil.NTRIPLES);

        model.write(System.out, SparqlUtil.RDF_XML);

        // Creating the first named graph and updating it with RDF data
        String sparqlUpdate = insertData(conn.dataEndpoint + "/zigovi/metadata", new String(out.toByteArray()));
        System.out.println(sparqlUpdate);
        // UpdateRequest represents a unit of execution
        UpdateRequest update = UpdateFactory.create(sparqlUpdate);

        UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, conn.updateEndpoint);

        processor.execute();


        // populating what has been inserted
        String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + "/zigovi/metadata", "?s ?p ?o");

        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);


        // Query the collection, dump output response as XML
        ResultSet results = query.execSelect();

        ResultSetFormatter.out(System.out, results);

        query.close() ;
    }

    public String extractMetadataToRdf(InputStream in, String id) {
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

        String outputFilePath = "./src/main/resources/static/extracted_rdf.xml";

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

    public void transformRDF(String xmlData, String xsltFIlePath, String outputPath) {
        String path = "./src/main/resources/xml/ZH-1-generated.xml";
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

    public String readFromFile(String filePath) {
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

    private org.w3c.dom.Document buildDocument(String filePath) {

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

    public String insertData(String graphURI, String ntriples) {
        return String.format("INSERT DATA { GRAPH <%1$s> { %2$s } }", graphURI, ntriples);
    }

    public static String readFile(String path, Charset encoding) throws IOException{
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded,encoding);
    }

    public List<SearchResultsDto> metaDataSelect(List<String> prijave) {
        String sparqlQuery = null;
        String querySelectPath = "./data/sparql/pendingRequests1.rq";

        List<SearchResultsDto> result = new ArrayList<>();
        try {
            sparqlQuery = String.format(readFile(querySelectPath, StandardCharsets.UTF_8));
        }catch (IOException e){}

        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(connectionProperties.queryEndpoint, sparqlQuery);

        // Query the SPARQL endpoint, iterate over the result set...
        ResultSet results = query.execSelect();

        String varName;
        RDFNode varValue;

        List<String> brojeviPrijaveZiga = new ArrayList<>();

        while (results.hasNext()) {

            // A single answer from a SELECT query
            QuerySolution querySolution = results.next();
            String br = querySolution.get("brojPrijaveZiga").toString();
            if (!prijave.contains(br)) {
                String email = querySolution.get("podnosilacEmail").toString();
                if(!brojeviPrijaveZiga.contains(br))
                {
                    result.add(new SearchResultsDto(br, email));
                    brojeviPrijaveZiga.add(br);
                }
            }
        }

        return result;
    }


    public String selectData(String graphURI, String sparqlCondition) {
        return String.format("SELECT * FROM <%1$s> WHERE { %2$s }", graphURI, sparqlCondition);
    }

    public void uploadResenjeMetadata(String extractedRdfFilePath, String SPARQL_NAMED_GRAPH_URI) throws IOException { //String filePath = "./src/main/resources/static/extracted_rdf.xml";  graphURI = "/resenje"
        // Creates a default model
        Model model = ModelFactory.createDefaultModel();
        model.read(extractedRdfFilePath);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        model.write(out, SparqlUtil.NTRIPLES);

        model.write(System.out, SparqlUtil.RDF_XML);

        // Creating the first named graph and updating it with RDF data
        String sparqlUpdate = insertData(connectionProperties.dataEndpoint + SPARQL_NAMED_GRAPH_URI, new String(out.toByteArray()));
        System.out.println(sparqlUpdate);
        // UpdateRequest represents a unit of execution
        UpdateRequest update = UpdateFactory.create(sparqlUpdate);

        UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, connectionProperties.updateEndpoint);

        processor.execute();
    }

    public List<SearchResultsDto> getPendingRequests(String querySelectPath) {     //String querySelectPath = "./data/sparql/pendingRequests.rq";
        String sparqlQuery = null;

        List<String> result = new ArrayList<>();
        try {
            sparqlQuery = String.format(readFile(querySelectPath, StandardCharsets.UTF_8));
        }catch (IOException e){}

        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(connectionProperties.queryEndpoint, sparqlQuery);

        // Query the SPARQL endpoint, iterate over the result set...
        ResultSet results = query.execSelect();

        String varName;
        RDFNode varValue;

        while (results.hasNext()) {
            QuerySolution querySolution = results.next();
            Iterator<String> variableBindings = querySolution.varNames();

            String broj = querySolution.get("brojPrijaveZiga").toString();
            result.add(broj);
        }
        return metaDataSelect(result);
    }

    public void updateBrojResenjaMetaInZahtev(String extractedRdfFilePath, String xmlData, String brojPrijave, String noviBrojResenja){
        Model model = ModelFactory.createDefaultModel();
        model.read(extractedRdfFilePath);

        Resource resource = model.createResource("http://www.ftn.uns.ac.rs/xwt/zahtev/" + brojPrijave);
        Property property1 = model.createProperty("http://www.ftn.uns.ac.rs/rdf/examples/predicate/", "broj_resenja");

        System.out.println("NOVI: "+noviBrojResenja);
        Literal literal1 = model.createLiteral(noviBrojResenja);
        Statement statement1 = model.createStatement(resource, property1, literal1);
        model.add(statement1);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        model.write(out, SparqlUtil.NTRIPLES);

        model.write(System.out, SparqlUtil.RDF_XML);
        String sparqlUpdate = SparqlUtil.insertData(connectionProperties.dataEndpoint + "/graph/metadata/zh1", out.toString());
        System.out.println(sparqlUpdate);

        // UpdateRequest represents a unit of execution
        UpdateRequest update = UpdateFactory.create(sparqlUpdate);

        // UpdateProcessor sends update request to a remote SPARQL update service.
        UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, connectionProperties.updateEndpoint);
        processor.execute();
    }
}
