package com.xml.xmlbackendzh1;

import com.xml.xmlbackendzh1.model.zh1.Zahtev;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@SpringBootApplication
public class XmlBackendZh1Application {

	public static void main(String[] args) throws JAXBException {
//		SpringApplication.run(XmlBackendZh1Application.class, args);
		JAXBContext context = JAXBContext.newInstance(Zahtev.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Zahtev zahtev = (Zahtev) unmarshaller.unmarshal(new File("./data/ZH-1-generated.xml"));


		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(zahtev, System.out);

	}

}
