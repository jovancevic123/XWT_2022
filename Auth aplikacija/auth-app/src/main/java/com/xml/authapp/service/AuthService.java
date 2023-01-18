package com.xml.authapp.service;

import com.xml.authapp.dao.AuthDaoLayer;
import com.xml.authapp.dto.LoginDto;
import com.xml.authapp.dto.RegisterDto;
import com.xml.authapp.exceptions.UserNotFoundException;
import com.xml.authapp.model.User;
import org.bouncycastle.openssl.PasswordException;
import org.exist.util.StringInputSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringWriter;

@Service
public class AuthService {
    private AuthDaoLayer daoLayer;

    @Autowired
    public AuthService(AuthDaoLayer dao){
        this.daoLayer = dao;
    }

    public XMLResource findUserByEmail(String email) throws XMLDBException {
        return this.daoLayer.findById(email + ".xml", "/db/patent/users");
    }

    public void register(RegisterDto dto) throws Exception {
        User user = new User(dto);

        JAXBContext context = JAXBContext.newInstance(User.class);
        StringWriter stringWriter = new StringWriter();
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(user, stringWriter);

        this.daoLayer.save(user.getEmail(), stringWriter.toString(), "/db/patent/users");
    }

    public User login(LoginDto dto) throws XMLDBException, JAXBException, PasswordException {
        XMLResource res = findUserByEmail(dto.getEmail());
        if(res == null){
            throw new UserNotFoundException();
        }

        JAXBContext context = JAXBContext.newInstance(User.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        User user = (User) unmarshaller.unmarshal(new StringInputSource(res.getContent().toString()));
        if(!user.getPassword().equals(dto.getPassword())){
            throw new PasswordException("Password is not correct!");
        }
        return user;
    }

}
