package com.xml.backend.p1;

import com.xml.backend.p1.model.Zahtev;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import java.io.File;

@SpringBootApplication
public class XmlBackendP1Application {

	public static void main(String[] args)
	{
		//SpringApplication.run(com.xml.backend.p1.XmlBackendP1Application.class, args);
		try{
			JAXBContext context = JAXBContext.newInstance(Zahtev.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Zahtev zahtev = (Zahtev) unmarshaller.unmarshal(new File("./data/P-1-generated.xml"));

			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(zahtev, System.out);

		}catch (Exception ex){
			System.out.println(ex.getMessage());
		}

	}

}
