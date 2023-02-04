package com.xml.xmlbackend.controller;

import com.xml.xmlbackend.dto.*;
import com.xml.xmlbackend.model.a1.Resenje;
import com.xml.xmlbackend.service.A1DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.util.List;

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
        this.service.addAutorskoDelo(dto);
        return ResponseEntity.ok("Success");
    }

    @GetMapping(value="/basic-search", consumes = "application/xml", produces = "application/xml")
    public ResponseEntity<?> searchRequests(@RequestParam("textToSearch") String text) {
        List<SearchResultDto> resultDtoList = this.service.basicSearch(text);
        SearchResultsDto searchResultsDto = new SearchResultsDto(resultDtoList);
        return ResponseEntity.ok(searchResultsDto);
    }

    @GetMapping("/pdf")
    public ResponseEntity<?> getRequestPDF(@RequestParam("brojPrijave") String brojPrijave)  {
        try {
            ByteArrayResource body = this.service.getRequestPDF(brojPrijave);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(body);
        } catch (Exception e) {
            return (ResponseEntity<?>) ResponseEntity.badRequest();
        }
    }

    @GetMapping(value="/html")
    public ResponseEntity<?> getHtml(@RequestParam("brojPrijave") String brojPrijave)  {
        try {
            String html = this.service.getRequestHTML(brojPrijave);
            return ResponseEntity.ok(html);
        } catch (Exception e) {
            return (ResponseEntity<?>) ResponseEntity.badRequest();
        }
    }


    @GetMapping("/json")
    public ResponseEntity<?> getMetadataJSON(@RequestParam("brojPrijave") String brojPrijave) {
        try {
            String json = this.service.getMetadataJSON(brojPrijave);
            return ResponseEntity.ok(json);
        } catch (Exception e) {
            return (ResponseEntity<?>) ResponseEntity.badRequest();
        }
    }

    @GetMapping(value="/rdf", produces = "application/xml")
    public ResponseEntity<?> getMetadataRDF(@RequestParam("brojPrijave") String brojPrijave) {
        try {
            String rdfXml = this.service.getMetadataRDF(brojPrijave);
            return ResponseEntity.ok(rdfXml);
        } catch (Exception e) {
            return (ResponseEntity<?>) ResponseEntity.badRequest();
        }
    }

    @PostMapping(value="/approve-request", produces = "application/xml")
    public ResponseEntity<?> approveRequest(@RequestBody ResponseToPendingRequestDto dto){
        try{
            String brojResenja = this.service.approveRequest(dto);
            return ResponseEntity.ok(brojResenja);
        }catch(Exception ex){
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value="/reject-request", produces = "application/xml")
    public ResponseEntity<?> rejectRequest(@RequestBody ResponseToPendingRequestDto dto){
        try{
            String brojResenja = this.service.rejectRequest(dto);
            return ResponseEntity.ok(brojResenja);
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

    @PostMapping(value="/advanced-search", produces = "application/xml")
    public ResponseEntity<?> advancedSearch(@RequestBody AdvancedSearchListDto dto){
        try{
            SearchResultsDto requestDtos = new SearchResultsDto(this.service.advancedSearch(dto));
            return ResponseEntity.ok(requestDtos);
        }catch(Exception ex){
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value="/report", produces = "application/xml")
    public ResponseEntity<?> generateReport(@RequestParam("start") String startDate, @RequestParam("end") String endDate){
        try{
            ByteArrayResource body = this.service.generateReport(startDate, endDate);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(body);
        }catch(Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
