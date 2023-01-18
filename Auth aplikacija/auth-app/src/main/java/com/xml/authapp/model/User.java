package com.xml.authapp.model;

import com.xml.authapp.dto.RegisterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "firstname", "lastname", "email", "password", "role"
})
@XmlRootElement(name = "user")
public class User {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String role;

    public User(RegisterDto dto){
        this.firstname = dto.getFirstname();
        this.lastname = dto.getLastname();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.role = Objects.equals(dto.getRole(), "1") ? "KORISNIK" : "SLUZBENIK";
    }
}
