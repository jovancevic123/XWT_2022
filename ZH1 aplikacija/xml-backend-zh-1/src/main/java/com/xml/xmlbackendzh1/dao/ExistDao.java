package com.xml.xmlbackendzh1.dao;

import com.xml.xmlbackendzh1.model.zh1.Resenje;
import com.xml.xmlbackendzh1.model.zh1.Zahtev;
import com.xml.xmlbackendzh1.util.AuthenticationUtilities;
import org.exist.util.StringInputSource;
import org.springframework.stereotype.Repository;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;

@Repository
public class ExistDao {

    private AuthenticationUtilities.ConnectionProperties connectionProperties;

    public ExistDao() throws Exception {
        this.connectionProperties = AuthenticationUtilities.loadProperties();

        // initialize database driver
        System.out.println("[INFO] Loading driver class: " + connectionProperties.driver);
        Class<?> cl = Class.forName(connectionProperties.driver);

        Database database = (Database) cl.getDeclaredConstructor().newInstance();
        database.setProperty("create-database", "true");

        DatabaseManager.registerDatabase(database);
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

    public XMLResource findById(String resourceId, String collectionId) throws XMLDBException {
        Collection collection = getOrCreateCollection(collectionId);
//        collection = DatabaseManager.getCollection(connectionProperties.uri + collectionId);
        collection.setProperty(OutputKeys.INDENT, "yes");

        XMLResource res = (XMLResource)collection.getResource(resourceId);

        return res;
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

    public Resenje findUnmarshalledResenjeById(String resourceId) throws XMLDBException, JAXBException {
        XMLResource res = this.findById(resourceId + ".xml", "/db/zig/resenja");
        JAXBContext context = JAXBContext.newInstance(Resenje.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        return (Resenje) unmarshaller.unmarshal(new StringInputSource(res.getContent().toString()));
    }

    public Zahtev findUnmarshalledZahtevById(String resourceId) throws XMLDBException, JAXBException {
        XMLResource res = this.findById(resourceId + ".xml", "/db/zig/zahtevi");
        JAXBContext context = JAXBContext.newInstance(Zahtev.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        return (Zahtev) unmarshaller.unmarshal(new StringInputSource(res.getContent().toString()));
    }
}
