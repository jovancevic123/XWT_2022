package com.xml.backend.p1.service;

import com.xml.backend.p1.dao.ExistDao;
import com.xml.backend.p1.dto.SearchResultsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;

import java.util.List;

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
}
