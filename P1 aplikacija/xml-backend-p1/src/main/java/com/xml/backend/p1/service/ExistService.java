package com.xml.backend.p1.service;

import com.xml.backend.p1.dao.P1DocumentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExistService {

    private P1DocumentDAO daoLayer;

    @Autowired
    public ExistService(P1DocumentDAO dao){
        this.daoLayer = dao;
    }
}
