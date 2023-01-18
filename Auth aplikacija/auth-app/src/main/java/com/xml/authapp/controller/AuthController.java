package com.xml.authapp.controller;

import com.xml.authapp.dto.LoginDto;
import com.xml.authapp.dto.RegisterDto;
import com.xml.authapp.model.User;
import com.xml.authapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService service;

    @Autowired
    public AuthController(AuthService service){
        this.service = service;
    }

    @PostMapping(value="/login", consumes = "application/xml", produces = "application/xml")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            User u = this.service.login(loginDto);
            return ResponseEntity.ok(u);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value="/register", consumes = "application/xml", produces = "application/xml")
    public ResponseEntity<?> register(@RequestBody RegisterDto dto) {
        try {
            this.service.register(dto);
            return ResponseEntity.ok("You have successfully registrated!");
        } catch (Exception e) {
            return new ResponseEntity<String>("Something went wrong, try again!", HttpStatus.BAD_REQUEST);
        }
    }
}
