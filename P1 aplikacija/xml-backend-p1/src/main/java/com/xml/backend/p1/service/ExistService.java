package com.xml.backend.p1.service;

import com.xml.backend.p1.dao.ExistDao;
import com.xml.backend.p1.dto.SearchResultsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;

import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;

import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

@Service
public class ExistService {

    private ExistDao daoLayer;

    @Autowired
    public ExistService(ExistDao dao){
        this.daoLayer = dao;
    }

    public List<SearchResultsDto> documentsThatReferences(String documentId) throws XMLDBException {
        return this.daoLayer.getDocumentsThatReferences(documentId, "/db/patent/zahtevi");
    }

    public List<SearchResultsDto> documentsThatReferencedIn(String documentId) throws XMLDBException {
        return this.daoLayer.getDocumentsThatAreReferencedIn(documentId, "/db/patent/zahtevi");
    }

    public boolean validateXMLSchema(String xsdPath, String xmlPath){
        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException e){
            System.out.println("Exception: "+e.getMessage());
            return false;
        }catch(SAXException e1){
            System.out.println("SAX Exception: "+e1.getMessage());
            return false;
        }

        return true;

    }
}
