package com.xml.authapp.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "xml")
public class RegisterDto {

    @JacksonXmlProperty(localName = "firstname")
    @JacksonXmlCData
    private String firstname;

    @JacksonXmlProperty(localName = "lastname")
    @JacksonXmlCData
    private String lastname;

    @JacksonXmlProperty(localName = "email")
    @JacksonXmlCData
    private String email;

    @JacksonXmlProperty(localName = "password")
    @JacksonXmlCData
    private String password;

    @JacksonXmlProperty(localName = "role")
    @JacksonXmlCData
    private String role;
}
