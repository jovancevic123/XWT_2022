package com.xml.xmlbackendzh1.controller;

import com.xml.xmlbackendzh1.dto.AdvancedSearchListDto;
import com.xml.xmlbackendzh1.dto.RequestDto;
import com.xml.xmlbackendzh1.dto.ResponseToPendingRequestDto;
import com.xml.xmlbackendzh1.dto.SearchResultsListDto;
import com.xml.xmlbackendzh1.model.zh1.Resenje;
import com.xml.xmlbackendzh1.service.ZH1DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @PostMapping(value="/reject-request", produces = "application/xml")
    public ResponseEntity<?> rejectRequest(@RequestBody ResponseToPendingRequestDto dto){
        try{
            this.service.rejectRequest(dto);
            return ResponseEntity.ok("Success");
        }catch(Exception ex){
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value="/get-resenje", produces = "application/xml")
    public ResponseEntity<?> getResenjeById(@RequestParam("documentId") String documentId){
        try{
            Resenje r = this.service.findResenjeById(documentId);
            return ResponseEntity.ok(r);
        }catch(Exception ex){
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/pdf")
    public ResponseEntity<?> getRequestPDF(@RequestParam("brojPrijaveZiga") String brojPrijave){
        try{
            ByteArrayResource body = this.service.getRequestPDF(brojPrijave);
            return ((ResponseEntity.BodyBuilder)ResponseEntity.ok()).contentType(MediaType.APPLICATION_OCTET_STREAM).body(body);
        }catch(Exception ex){
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/html")
    public ResponseEntity<?> getRequestHTML(@RequestParam("brojPrijaveZiga") String brojPrijave){
        try{
            String html = this.service.getRequestHTML(brojPrijave);
            return ResponseEntity.ok(html);
        }catch(Exception ex){
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value="/rdf", produces = "application/xml")
    public ResponseEntity<?> getMetadataRDF(@RequestParam("brojPrijaveZiga") String brojPrijaveZiga){
        try{
            String rdfXml = this.service.getMetadataRDF(brojPrijaveZiga);
            return ResponseEntity.ok(rdfXml);
        }catch(Exception ex){
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/json")
    public ResponseEntity<?> getMetadataJSON(@RequestParam("brojPrijaveZiga") String brojPrijaveZiga){
        try{
            String json = this.service.getMetadataJSON(brojPrijaveZiga);
            return ResponseEntity.ok(json);
        }catch(Exception ex){
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value="/basic-search", produces = "application/xml")
    public ResponseEntity<?> basicSearch(@RequestParam("textToSearch") String text){
        try{
            SearchResultsListDto requestDtos = new SearchResultsListDto(this.service.basicSearch(text));
            return ResponseEntity.ok(requestDtos);
        }catch(Exception ex){
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value="/basic-search-user", produces = "application/xml")
    public ResponseEntity<?> basicSearchUser(@RequestParam("textToSearch") String text, @RequestParam("email") String email){
        try{
            SearchResultsListDto requestDtos = new SearchResultsListDto(this.service.basicSearchUser(text, email));
            return ResponseEntity.ok(requestDtos);
        }catch(Exception ex){
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value="/advanced-search", produces = "application/xml")
    public ResponseEntity<?> advancedSearch(@RequestBody AdvancedSearchListDto dto){
        try{
            SearchResultsListDto requestDtos = new SearchResultsListDto(this.service.advancedSearch(dto));
            return ResponseEntity.ok(requestDtos);
        }catch(Exception ex){
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value="/report", produces = "application/xml")
    public ResponseEntity<?> generateReport(@RequestParam("start") String startDate, @RequestParam("end") String endDate){
        try{
            ByteArrayResource body = this.service.generateReport(startDate, endDate);
            return ((ResponseEntity.BodyBuilder)ResponseEntity.ok()).contentType(MediaType.APPLICATION_OCTET_STREAM).body(body);
        }catch(Exception ex){
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value="/user-requests", produces = "application/xml")
    public ResponseEntity<?> getUsersRequests(@RequestParam("email") String email){
        try{
            SearchResultsListDto requestDtos = new SearchResultsListDto(this.service.getUsersRequests(email));
            return ResponseEntity.ok(requestDtos);
        }catch(Exception ex){
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}