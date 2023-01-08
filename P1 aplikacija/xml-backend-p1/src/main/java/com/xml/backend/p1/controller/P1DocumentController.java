package com.xml.backend.p1.controller;

import com.xml.backend.p1.dto.RequestDto;
import com.xml.backend.p1.service.P1DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xmldb.api.base.XMLDBException;;import javax.xml.bind.JAXBException;

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

    @PostMapping(value="/add-request", consumes = "application/xml", produces = "application/xml")
    public ResponseEntity<?> submitRequest(@RequestBody RequestDto dto) throws JAXBException {
        System.out.println(dto);
        this.service.addPatent(dto);
        return ResponseEntity.ok("Bravo");
    }
}
