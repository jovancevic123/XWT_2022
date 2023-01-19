package com.xml.backend.p1;

import com.xml.backend.p1.dao.ExistDao;
import com.xml.backend.p1.service.MetadataService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

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


		SpringApplication.run(com.xml.backend.p1.XmlBackendP1Application.class, args);






//		String xmlData = Files.readString(Paths.get("./src/main/resources/xml/P-1-generated.xml"));

		// TACKA 2

		//PISANJE
//		P1DocumentDAO dao = new P1DocumentDAO();
//		dao.save("id888", xmlData);
//		//CITANJE
//		XMLResource res = dao.findById("id888.xml");
//		System.out.println(res.getContent());

		// TACKA 3

//		String xsltFIlePath = "./src/main/resources/xml/metadata.xsl";
//		String outputPath = "./src/main/resources/static/rdf/";
//		MetadataService service = new MetadataService(new ExistDao());
//
//		service.transformRDF(xmlData, xsltFIlePath, outputPath); // 1. xml u obliku string-a
//		String resultMeta = service.extractMetadataToRdf(new FileInputStream(new File("./src/main/resources/static/rdf")), "./src/main/resources/static/extracted_rdf.xml");
//
//		service.uploadZahtevMetadata("/graph/metadata/p1");
//


		// TACKA 4
//		XmlTransformer transformer = new XmlTransformer();
//		transformer.transformToHtml(xmlData);
//		transformer.transformToPdf(xmlData);
//
//		try{
//
//			// Unmarshalling
//			JAXBContext context = JAXBContext.newInstance(Resenje.class);
//			Unmarshaller unmarshaller = context.createUnmarshaller();
//			Resenje resenje = (Resenje) unmarshaller.unmarshal(new File("./src/main/resources/xml/P-1-resenje-generated.xml"));
//
//			resenje.setBrojPrijave("P-78777");
//
//			// Marshalling to stdout
//			Marshaller m = context.createMarshaller();
//			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//			m.marshal(resenje, System.out);
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
