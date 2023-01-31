package com.xml.xmlbackend.dao;


import com.xml.xmlbackend.util.AuthenticationUtilities;
import org.springframework.stereotype.Repository;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

import javax.xml.transform.OutputKeys;
import java.io.IOException;

//@Repository
public class A1DocumentDAO {

    private AuthenticationUtilities.ConnectionProperties connectionProperties;
    private String collectionId;
    private Collection collection;

    public A1DocumentDAO() throws Exception {
        this.connectionProperties = AuthenticationUtilities.loadProperties();
        this.collectionId = "/db/autorskoDelo";

        // initialize database driver
        System.out.println("[INFO] Loading driver class: " + connectionProperties.driver);
        Class<?> cl = Class.forName(connectionProperties.driver);

        Database database = (Database) cl.getDeclaredConstructor().newInstance();
        database.setProperty("create-database", "true");

        DatabaseManager.registerDatabase(database);

        collection = getOrCreateCollection(collectionId, 0);
//        collection = DatabaseManager.getCollection(connectionProperties.uri + collectionId);
        collection.setProperty(OutputKeys.INDENT, "yes");
    }

    public XMLResource findById(String resourceId) throws XMLDBException {
        XMLResource res = (XMLResource)collection.getResource(resourceId);
        return res;
    }

    public void save(String documentId, String xmlData) throws Exception {
        XMLResource res = (XMLResource) collection.createResource( documentId + ".xml", XMLResource.RESOURCE_TYPE);
        res.setContent(xmlData);
        System.out.println("[INFO] Storing the document: " + res.getId());
        collection.storeResource(res);
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