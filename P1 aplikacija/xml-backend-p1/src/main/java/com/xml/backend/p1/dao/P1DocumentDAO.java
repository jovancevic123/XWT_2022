package com.xml.backend.p1.dao;

import com.xml.backend.p1.util.AuthenticationUtilities;
import com.xml.backend.p1.util.AuthenticationUtilities.ConnectionProperties;
import org.springframework.stereotype.Repository;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import javax.xml.transform.OutputKeys;

@Repository
public class P1DocumentDAO {

    private ConnectionProperties connectionProperties;
    private String collectionId;
    private Collection collection;

    public P1DocumentDAO() throws Exception {
        this.connectionProperties = AuthenticationUtilities.loadProperties();
        this.collectionId = "/db/sample/library";

        // initialize database driver
        System.out.println("[INFO] Loading driver class: " + connectionProperties.driver);
        Class<?> cl = Class.forName(connectionProperties.driver);

        Database database = (Database) cl.getDeclaredConstructor().newInstance();
        database.setProperty("create-database", "true");

        DatabaseManager.registerDatabase(database);

        collection = DatabaseManager.getCollection(connectionProperties.uri + collectionId);
        collection.setProperty(OutputKeys.INDENT, "yes");
    }

    public void findById(String resourceId) throws XMLDBException {
        XMLResource res = (XMLResource)collection.getResource(resourceId);
        System.out.println("[INFO] Showing the document as XML resource: ");
        System.out.println(res.getContent());
    }
}
