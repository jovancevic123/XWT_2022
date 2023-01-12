package com.xml.xmlbackendzh1.controller;

import com.xml.xmlbackendzh1.dto.RequestDto;
import com.xml.xmlbackendzh1.service.ZH1DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xmldb.api.base.XMLDBException;

import javax.xml.bind.JAXBException;

@RestController
@RequestMapping("/p1")
public class ZH1DocumentController {

    private ZH1DocumentService service;

    @Autowired
    public ZH1DocumentController(ZH1DocumentService service){
        this.service = service;
    }

    @GetMapping
    public void findById(@RequestParam String resourceId) throws XMLDBException {
        this.service.findById(resourceId);
    }

    @PostMapping(value="/add-request", consumes = "application/xml", produces = "application/xml")
    public ResponseEntity<?> submitRequest(@RequestBody RequestDto dto) throws JAXBException {
        System.out.println(dto);
        this.service.addZig(dto);
        return ResponseEntity.ok("Bravo");
    }
}