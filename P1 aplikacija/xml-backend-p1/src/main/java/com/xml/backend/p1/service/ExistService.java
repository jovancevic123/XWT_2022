package com.xml.backend.p1.service;

import com.xml.backend.p1.dao.ExistDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExistService {

    private ExistDao daoLayer;

    @Autowired
    public ExistService(ExistDao dao){
        this.daoLayer = dao;
    }
}
