package com.xml.backend.p1.controller;

import com.xml.backend.p1.dto.*;
import com.xml.backend.p1.model.Zahtev;
import com.xml.backend.p1.service.P1DocumentService;
import org.exist.util.StringInputSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xmldb.api.base.XMLDBException;;import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.util.List;

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
        this.service.findZahtevById(resourceId);
    }

    @PostMapping(value="/add-request", consumes = "application/xml", produces = "application/xml")
    public ResponseEntity<?> submitRequest(@RequestBody RequestDto dto) throws JAXBException {
        System.out.println(dto);
        this.service.addPatent(dto);
        return ResponseEntity.ok("Bravo");
    }

    @PostMapping(value="/add-request-xonomy", consumes = "application/xml", produces = "application/xml")
    public ResponseEntity<?> submitRequestXonomy(@RequestBody XonomyRequestDto dto) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Zahtev.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        Zahtev zahtev = (Zahtev) unmarshaller.unmarshal(new StringInputSource(dto.getRequest()));
        this.service.addPatentXonomy(zahtev);
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

    @GetMapping("/pdf")
    public ResponseEntity<?> getRequestPDF(@RequestParam("brojPrijave") String brojPrijave){
        try{
            ByteArrayResource body = this.service.getRequestPDF(brojPrijave);
            return ((ResponseEntity.BodyBuilder)ResponseEntity.ok()).contentType(MediaType.APPLICATION_OCTET_STREAM).body(body);
        }catch(Exception ex){
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/html")
    public ResponseEntity<?> getRequestHTML(@RequestParam("brojPrijave") String brojPrijave){
        try{
            String html = this.service.getRequestHTML(brojPrijave);
            return ResponseEntity.ok(html);
        }catch(Exception ex){
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/json")
    public ResponseEntity<?> getMetadataJSON(@RequestParam("brojPrijave") String brojPrijave){
        try{
            String json = this.service.getMetadataJSON(brojPrijave);
            return ResponseEntity.ok(json);
        }catch(Exception ex){
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/rdf")
    public ResponseEntity<?> getMetadataRDF(@RequestParam("brojPrijave") String brojPrijave){
        try{
            String rdfXml = this.service.getMetadataRDF(brojPrijave);
            return ResponseEntity.ok(rdfXml);
        }catch(Exception ex){
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value="/approve-request", produces = "application/xml")
    public ResponseEntity<?> approveRequest(@RequestBody ResponseToPendingRequestDto dto){
        try{
//            this.service.approveRequest(dto);
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

    @GetMapping("/report")
    public ResponseEntity<?> generateReport(@RequestParam("start") String startDate, @RequestParam("end") String endDate){
        try{
            ByteArrayResource body = this.service.generateReport(startDate, endDate);
            return ((ResponseEntity.BodyBuilder)ResponseEntity.ok()).contentType(MediaType.APPLICATION_OCTET_STREAM).body(body);
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

    @PostMapping(value="/advanced-search", produces = "application/xml")
    public ResponseEntity<?> advancedSearch(@RequestBody AdvancedSearchListDto dto){
        try{
            SearchResultsListDto requestDtos = new SearchResultsListDto(this.service.advancedSearch(dto));
            return ResponseEntity.ok(requestDtos);
        }catch(Exception ex){
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
