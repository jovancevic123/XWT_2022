package com.xml.backend.p1.dao;

import com.xml.backend.p1.dto.SearchResultsDto;
import com.xml.backend.p1.model.Resenje;
import com.xml.backend.p1.model.Zahtev;
import com.xml.backend.p1.util.AuthenticationUtilities;
import com.xml.backend.p1.util.AuthenticationUtilities.ConnectionProperties;
import org.exist.util.StringInputSource;
import org.exist.xmldb.EXistResource;
import org.springframework.stereotype.Repository;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExistDao {

    private ConnectionProperties connectionProperties;

    public ExistDao() throws Exception {
        this.connectionProperties = AuthenticationUtilities.loadProperties();

        // initialize database driver
        System.out.println("[INFO] Loading driver class: " + connectionProperties.driver);
        Class<?> cl = Class.forName(connectionProperties.driver);

        Database database = (Database) cl.getDeclaredConstructor().newInstance();
        database.setProperty("create-database", "true");

        DatabaseManager.registerDatabase(database);
    }

    public XMLResource findById(String resourceId, String collectionId) throws XMLDBException {
        Collection collection = getOrCreateCollection(collectionId);
//        collection = DatabaseManager.getCollection(connectionProperties.uri + collectionId);
        collection.setProperty(OutputKeys.INDENT, "yes");

        XMLResource res = (XMLResource)collection.getResource(resourceId);

        return res;
    }

    public Zahtev findUnmarshalledZahtevById(String resourceId) throws XMLDBException, JAXBException {
        XMLResource res = this.findById(resourceId + ".xml", "/db/patent/zahtevi");
        JAXBContext context = JAXBContext.newInstance(Zahtev.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        return (Zahtev) unmarshaller.unmarshal(new StringInputSource(res.getContent().toString()));
    }

    public Resenje findUnmarshalledResenjeById(String resourceId) throws XMLDBException, JAXBException {
        XMLResource res = this.findById(resourceId + ".xml", "/db/patent/resenja");
        JAXBContext context = JAXBContext.newInstance(Resenje.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        return (Resenje) unmarshaller.unmarshal(new StringInputSource(res.getContent().toString()));
    }

    public void save(String documentId, String xmlData, String collectionId) throws Exception {
        Collection collection = getOrCreateCollection(collectionId);
//        collection = DatabaseManager.getCollection(connectionProperties.uri + collectionId);
        collection.setProperty(OutputKeys.INDENT, "yes");

        XMLResource res = (XMLResource) collection.createResource( documentId + ".xml", XMLResource.RESOURCE_TYPE);
        res.setContent(xmlData);
        System.out.println("[INFO] Storing the document: " + res.getId());
        collection.storeResource(res);
    }

    public List<SearchResultsDto> getDocumentsThatReferences(String documentId, String collectionId) throws XMLDBException {
        Collection collection = getOrCreateCollection(collectionId);
        collection.setProperty(OutputKeys.INDENT, "yes");
        // get an instance of xpath query service
        XPathQueryService xpathService = (XPathQueryService) collection.getService("XPathQueryService", "1.0");
        xpathService.setNamespace("", "http://www.ftn.uns.ac.rs/xwt");
        String xpathExp = "declare variable $data as document-node()* := collection('/db/patent/zahtevi');\n" +
                "\n" +
                "for $v in $data\n" +
                "for $rp in $v/zahtev/ranije_prijave/ranija_prijava \n" +
                "where $rp/broj_prijave=" + documentId + "\n" +
                "return concat($v/zahtev/prijava/broj_prijave, ':', $v/zahtev/podnosilac/lice/kontakt/email, ':', $v/zahtev/pronalazak/naziv_pronalaska_srb) \n";

        ResourceSet result = xpathService.query(xpathExp);
        ResourceIterator i = result.getIterator();
        Resource res = null;
        List<SearchResultsDto> dtos = new ArrayList<>();

        while(i.hasMoreResources()) {
            try {
                res = i.nextResource();
                String[] tokens = res.getContent().toString().split(":");
                dtos.add(new SearchResultsDto(tokens[0], tokens[1], tokens[2]));
            } finally {
                try {
                    ((EXistResource)res).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
        return dtos;
    }

    public List<SearchResultsDto> getDocumentsThatAreReferencedIn(String documentId, String collectionId) throws XMLDBException {
        Collection collection = getOrCreateCollection(collectionId);
        collection.setProperty(OutputKeys.INDENT, "yes");
        // get an instance of xpath query service
        XPathQueryService xpathService = (XPathQueryService) collection.getService("XPathQueryService", "1.0");
        xpathService.setNamespace("", "http://www.ftn.uns.ac.rs/xwt");
        String xpathExp = "declare variable $data as document-node()* := collection('/db/patent/zahtevi');\n" +
                "\n" +
                "for $v in $data\n" +
                "for $rp in $v/zahtev/ranije_prijave/ranija_prijava \n" +
                "where $v/zahtev/prijava/broj_prijave=" + documentId + "\n" +
                "return $rp/broj_prijave/text() \n";

        ResourceSet result = xpathService.query(xpathExp);
        ResourceIterator i = result.getIterator();
        Resource res = null;
        List<SearchResultsDto> dtos = new ArrayList<>();

        while(i.hasMoreResources()) {
            try {
                res = i.nextResource();

                Zahtev z = findUnmarshalledZahtevById(res.getContent().toString());
                dtos.add(new SearchResultsDto(Integer.toString(z.getPrijava().getBrojPrijave()), z.getPodnosilac().getLice().getKontakt().getEmail(), z.getPronalazak().getNazivPronalaskaSRB()));
            } catch (JAXBException e) {
                e.printStackTrace();
            } finally {
                try {
                    ((EXistResource)res).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
        return dtos;
    }

    private Collection getOrCreateCollection(String collectionUri) throws XMLDBException {
        return getOrCreateCollection(collectionUri, 0);
    }

    private Collection getOrCreateCollection(String collectionUri, int pathSegmentOffset) throws XMLDBException {

        Collection col = DatabaseManager.getCollection(connectionProperties.uri + collectionUri, connectionProperties.user, connectionProperties.password);

        // create the collection if it does not exist
        if(col == null) {

            if(collectionUri.startsWith("/")) {
                collectionUri = collectionUri.substring(1);
            }

            String pathSegments[] = collectionUri.split("/");

            if(pathSegments.length > 0) {
                StringBuilder path = new StringBuilder();

                for(int i = 0; i <= pathSegmentOffset; i++) {
                    path.append("/" + pathSegments[i]);
                }

                Collection startCol = DatabaseManager.getCollection(connectionProperties.uri + path, connectionProperties.user, connectionProperties.password);

                if (startCol == null) {

                    // child collection does not exist

                    String parentPath = path.substring(0, path.lastIndexOf("/"));
                    Collection parentCol = DatabaseManager.getCollection(connectionProperties.uri + parentPath, connectionProperties.user, connectionProperties.password);

                    CollectionManagementService mgt = (CollectionManagementService) parentCol.getService("CollectionManagementService", "1.0");

                    System.out.println("[INFO] Creating the collection: " + pathSegments[pathSegmentOffset]);
                    col = mgt.createCollection(pathSegments[pathSegmentOffset]);

                    col.close();
                    parentCol.close();

                } else {
                    startCol.close();
                }
            }
            return getOrCreateCollection(collectionUri, ++pathSegmentOffset);
        } else {
            return col;
        }
    }
}
