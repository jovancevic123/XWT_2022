package com.xml.backend.p1.service;

import com.xml.backend.p1.dao.P1DocumentDAO;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;

@Service
@Getter
@Setter
public class P1DocumentService {

    private final P1DocumentDAO repository;

    @Autowired
    public P1DocumentService(P1DocumentDAO repository) {
        this.repository = repository;
    }

    public void findById(String resourceId) throws XMLDBException {
        this.repository.findById(resourceId);
    }
}
