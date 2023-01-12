package com.xml.xmlbackend;


import com.xml.xmlbackend.model.a1.FizickoLice;
import com.xml.xmlbackend.model.a1.Prilog;
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

		SpringApplication.run(XmlBackendApplication.class, args);

//		System.out.println("*********** Učitavanje dokumenta A-1 ***********");
//		JAXBContext context = JAXBContext.newInstance(Zahtev.class);
//		Unmarshaller unmarshaller = context.createUnmarshaller();
//		Zahtev zahtev = (Zahtev) unmarshaller.unmarshal(new File("./data/A-1-generated.xml"));
//
//		System.out.println("*********** Pisanje dokumenta A-1 ***********");
//
//		zahtev.getPodnosilac().setPodnosilacJeAutor(false);
//		FizickoLice podnosilac = (FizickoLice) zahtev.getPodnosilac().getLice();
//		podnosilac.setIme("Milan");
//		podnosilac.setPrezime("Milanović");
//		podnosilac.getKontakt().setEmail("milan@gmail.com");
//		podnosilac.getKontakt().setTelefon("0644598771");
//
//		Prilog prilog = new Prilog();
//		prilog.setVrstaPriloga("audio zapis");
//		prilog.setSrc("putanja do audio zapisa");
//		zahtev.getPrilozi().add(prilog);
//
//		zahtev.getAutorskoDelo().setFormaZapiseDela("štampani tekst");
//		zahtev.getAutorskoDelo().setVrstaDela("književno delo");
//
//		Marshaller m = context.createMarshaller();
//		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//		m.marshal(zahtev, System.out);

	}
}
