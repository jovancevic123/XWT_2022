package com.xml.xmlbackendzh1.service;

import com.xml.xmlbackendzh1.dao.ZH1DocumentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExistService {

    private ZH1DocumentDAO daoLayer;

    @Autowired
    public ExistService(ZH1DocumentDAO dao){
        this.daoLayer = dao;
    }
}
