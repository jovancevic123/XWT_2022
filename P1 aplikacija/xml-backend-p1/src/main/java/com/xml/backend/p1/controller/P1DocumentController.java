package com.xml.backend.p1.controller;

import com.xml.backend.p1.service.P1DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xmldb.api.base.XMLDBException;;

@RestController
@RequestMapping("/p1")
public class P1DocumentController {

    private P1DocumentService service;

    @Autowired
    public P1DocumentController(P1DocumentService service){
        this.service = service;
    }

    @GetMapping
    public void findById(@RequestParam String resourceId) throws XMLDBException {
        this.service.findById(resourceId);
    }
}
