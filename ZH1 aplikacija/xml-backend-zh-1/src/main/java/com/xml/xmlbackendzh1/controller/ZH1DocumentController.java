package com.xml.xmlbackendzh1.controller;

import com.xml.xmlbackendzh1.dto.RequestDto;
import com.xml.xmlbackendzh1.dto.ResponseToPendingRequestDto;
import com.xml.xmlbackendzh1.dto.SearchResultsListDto;
import com.xml.xmlbackendzh1.service.ZH1DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xmldb.api.base.XMLDBException;

import javax.xml.bind.JAXBException;

@RestController
@RequestMapping("/zh1")
public class ZH1DocumentController {

    private ZH1DocumentService service;

    @Autowired
    public ZH1DocumentController(ZH1DocumentService service){
        this.service = service;
    }

    @GetMapping
    public void findById(@RequestParam String resourceId) throws XMLDBException {
        this.service.findZahtevById(resourceId);
    }

    @PostMapping(value="/add-request", consumes = "application/xml", produces = "application/xml")
    public ResponseEntity<?> submitRequest(@RequestBody RequestDto dto) throws Exception {
        System.out.println(dto);
        this.service.addZahtevZig(dto);
        return ResponseEntity.ok("Bravo");
    }

    @GetMapping(value="/get-pending-requests", produces = "application/xml")
    public ResponseEntity<?> getPendingRequests(){
        try{
            SearchResultsListDto requestDtos = new SearchResultsListDto(this.service.getPendingRequests());
            return ResponseEntity.ok(requestDtos);
        }catch(Exception ex){
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value="/approve-request", produces = "application/xml")
    public ResponseEntity<?> approveRequest(@RequestBody ResponseToPendingRequestDto dto){
        try{
            this.service.approveRequest(dto);
            return ResponseEntity.ok("Success");
        }catch(Exception ex){
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}