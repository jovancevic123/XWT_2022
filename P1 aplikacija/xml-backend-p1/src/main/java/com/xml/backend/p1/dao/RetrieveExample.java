package com.xml.backend.p1.dao;

import com.xml.backend.p1.util.AuthenticationUtilities;
import com.xml.backend.p1.util.AuthenticationUtilities.ConnectionProperties;
import org.exist.xmldb.EXistResource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import javax.xml.transform.OutputKeys;

//THIS IS JUST EXAMPLE, NOTHING TO DO WITH THE PROJECT ITSELF
public class RetrieveExample {

    public static void main(String[] args) throws Exception {
        RetrieveExample.run(AuthenticationUtilities.loadProperties(), args);
    }

    public static void run(ConnectionProperties conn, String args[]) throws Exception {


        System.out.println("[INFO] " + RetrieveExample.class.getSimpleName());

        // initialize collection and document identifiers
        String collectionId = null;
        String documentId = null;

        if (args.length == 2) {

            System.out.println("[INFO] Passing the arguments... ");

            collectionId = args[0];
            documentId = args[1];
        } else {

            System.out.println("[INFO] Using defaults.");

            collectionId = "/db/sample/library";
            documentId = "1.xml";
        }

        System.out.println("\t- collection ID: " + collectionId);
        System.out.println("\t- document ID: " + documentId + "\n");

        // initialize database driver
        System.out.println("[INFO] Loading driver class: " + conn.driver);
        Class<?> cl = Class.forName(conn.driver);

        Database database = (Database) cl.getDeclaredConstructor().newInstance();
        database.setProperty("create-database", "true");

        DatabaseManager.registerDatabase(database);

        Collection col = null;
        XMLResource res = null;

        try {
            // get the collection
            System.out.println("[INFO] Retrieving the collection: " + collectionId);
            col = DatabaseManager.getCollection(conn.uri + collectionId);
            col.setProperty(OutputKeys.INDENT, "yes");

            System.out.println("[INFO] Retrieving the document: " + documentId);
            res = (XMLResource)col.getResource(documentId);

            if(res == null) {
                System.out.println("[WARNING] Document '" + documentId + "' can not be found!");
            } else {

                System.out.println("[INFO] Showing the document as XML resource: ");
                System.out.println(res.getContent());

            }
        } finally {
            //don't forget to clean up!

            if(res != null) {
                try {
                    ((EXistResource)res).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }

            if(col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
    }
}