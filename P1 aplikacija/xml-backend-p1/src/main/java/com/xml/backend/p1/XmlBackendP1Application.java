package com.xml.backend.p1;

import com.xml.backend.p1.dao.P1DocumentDAO;
import com.xml.backend.p1.model.Zahtev;
import com.xml.backend.p1.service.ExistService;
import com.xml.backend.p1.service.MetadataService;
import com.xml.backend.p1.transformers.XmlTransformer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static com.xml.backend.p1.transformers.XmlTransformer.*;

@SpringBootApplication
public class XmlBackendP1Application {

	public static void main(String[] args) throws Exception {

//		String xmlData = null;
//		try {
//			xmlData = new String(Files.readAllBytes(Paths.get("./src/main/resources/xml/P-1-generated.xml")), StandardCharsets.UTF_8);
//
//			P1DocumentDAO dao = new P1DocumentDAO();
//			dao.save("787", xmlData);
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		String xsltFIlePath = "./src/main/resources/xml/metadata.xsl";
//		String outputPath = "./src/main/resources/static/rdf/";


//		SpringApplication.run(com.xml.backend.p1.XmlBackendP1Application.class, args);






		String xmlData = new String(Files.readAllBytes(Paths.get("./src/main/resources/xml/P-1-generated.xml")), StandardCharsets.UTF_8);

		// TACKA 2

		//PISANJE
//		P1DocumentDAO dao = new P1DocumentDAO();
//		dao.save("id888", xmlData);
//		//CITANJE
//		XMLResource res = dao.findById("id888.xml");
//		System.out.println(res.getContent());

		// TACKA 3

		String xsltFIlePath = "./src/main/resources/xml/metadata.xsl";
		String outputPath = "./src/main/resources/static/rdf/";
		MetadataService service = new MetadataService();

		service.transformRDF(xmlData, xsltFIlePath, outputPath); // 1. xml u obliku string-a
		String resultMeta = service.extractMetadataToRdf(new FileInputStream(new File("./src/main/resources/static/rdf")), "123");

		service.uploadMetadata();



		// TACKA 4
//		XmlTransformer transformer = new XmlTransformer();
//		transformer.transformToHtml(xmlData);
//		transformer.transformToPdf(xmlData);

//		try{
//
//			// Unmarshalling
//			JAXBContext context = JAXBContext.newInstance(Zahtev.class);
//			Unmarshaller unmarshaller = context.createUnmarshaller();
//			Zahtev zahtev = (Zahtev) unmarshaller.unmarshal(new File("./src/main/resources/xml/P-1-generated.xml"));
//
//			// Changing properties in request
//			zahtev.getPronalazak().setNazivPronalaskaSRB("Sistem napajanja za električna vozila");
//			zahtev.getPronalazak().setNazivPronalaskaENG("Battery system for electric vehicles");
//
//			zahtev.getPodnosilac().getLice().getAdresa().setUlica("Bulevar vojvode Stepe");
//
//			// Marshalling to stdout
//			Marshaller m = context.createMarshaller();
//			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//			m.marshal(zahtev, System.out);
//
//		}catch (Exception ex){
//			System.out.println(ex.getMessage());
//		}

	}

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Authorization", "Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers", "responseType"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "responseType"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}
}
