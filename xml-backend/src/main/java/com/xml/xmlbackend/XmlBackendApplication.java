package com.xml.xmlbackend;

import com.xml.xmlbackend.model.a1.Zahtev;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@SpringBootApplication
public class XmlBackendApplication {

	public static void main(String[] args) throws JAXBException {

//		SpringApplication.run(XmlBackendApplication.class, args);

		System.out.println("NESTOO");

		System.out.println(com.xml.xmlbackend.XmlBackendApplication.class);


		JAXBContext context = JAXBContext.newInstance(com.xml.xmlbackend.model.a1.Zahtev.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Zahtev zahtev = (Zahtev) unmarshaller.unmarshal(new File("./data/A-1-generated.xml"));
	}

}
