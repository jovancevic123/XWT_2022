package com.xml.xmlbackend;


import com.xml.xmlbackend.model.a1.Zahtev;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@SpringBootApplication
public class XmlBackendApplication {

	public static void main(String[] args) throws JAXBException {

//		SpringApplication.run(XmlBackendApplication.class, args);

		JAXBContext context = JAXBContext.newInstance(Zahtev.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Zahtev zahtev = (Zahtev) unmarshaller.unmarshal(new File("./data/A-1-generated.xml"));


		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(zahtev, System.out);

		System.out.println(zahtev.getPodnosilac().getLice());
	}
}
