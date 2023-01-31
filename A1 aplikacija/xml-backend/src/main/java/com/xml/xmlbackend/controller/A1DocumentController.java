package com.xml.xmlbackend.controller;

import com.xml.xmlbackend.dto.ZahtevRequestDto;
import com.xml.xmlbackend.service.A1DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/a1")
public class A1DocumentController {
    private final A1DocumentService service;

    @Autowired
    public A1DocumentController(A1DocumentService a1DocumentService){
        this.service = a1DocumentService;
    }


    @PostMapping(value="/add-request", consumes = "application/xml", produces = "application/xml")
    public ResponseEntity<?> submitRequest(@RequestBody ZahtevRequestDto dto) throws Exception {
        try{
            this.service.addAutorskoDelo(dto);
            return ResponseEntity.ok("Success");
        }
        catch(Exception ex){
            return new ResponseEntity<String>("Xml document is not valid!", HttpStatus.BAD_REQUEST);
        }
    }

}
