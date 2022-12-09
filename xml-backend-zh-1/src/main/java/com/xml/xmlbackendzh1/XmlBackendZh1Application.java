//svaki jednostavni element sam samo dodao setujuci vrednost
//za svaki kompleksni element (onaj koji ima vise podelemenata), napravio sam posebnu funkciju gde sam ga inicijalizovao i popuinio njegove podelemente
//ako je i podelement kompleksni element, za njega takodje vazi ovaj proces
//ukoliko je popunjavanje lista u pitanju, njih sam popunjavao pomocu .getLista().add()
//Prilozi kao lista je tretirana drugacije, s obzirom da njeni elementi nisu isti i jedinstveno su navedeni u dokumentu

package com.xml.xmlbackendzh1;
import com.xml.xmlbackendzh1.model.common.LocalDateAdapter;
import com.xml.xmlbackendzh1.model.zh1.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class XmlBackendZh1Application {

	public static void main(String[] args) throws JAXBException {
//		SpringApplication.run(XmlBackendZh1Application.class, args);
		XmlBackendZh1Application xmlBackendZh1Application = new XmlBackendZh1Application();
		xmlBackendZh1Application.kt2Demo();
	}

	public void kt2Demo() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Zahtev.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Zahtev zahtev = (Zahtev) unmarshaller.unmarshal(new File("./data/ZH-1-generated.xml"));

		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(zahtev, System.out);

		// Kreira se novi zahtev
		zahtev = createZahtev();

		// Serijalizacija objektnog modela u XML
		System.out.println("\n\n\n=================[INFO] Marshalling customized zahtev: ==================================\n\n\n");
		m.marshal(zahtev, System.out);
	}

	private Zahtev createZahtev() {
		Zahtev zahtev = new Zahtev();
		zahtev.setNaziv("ZAHTEV ZA PRIZNANJE ZIGA");

		zahtev.setZavod(createZavod());

		zahtev.setPodnosilac(createPodnosilac());

		zahtev.setPodnosilac(createPunomocnik());

		zahtev.setZig(createZig());

		zahtev.getPlaceneTakse().add(createTaksa1());
		zahtev.getPlaceneTakse().add(createTaksa2());

		zahtev.setPrilozi(createPrilozi());

		return zahtev;
	}

	private Prilozi createPrilozi() {
		Prilozi prilozi = new Prilozi();
		prilozi.setPrimerakZnaka(createPrilog("primerakZnakaPutanja"));
		prilozi.setSpisakRobeIUsluga(createPrilog("spisakRobeIUslugaPutanja"));
		prilozi.setPunomocje(createPrilog("punomocjePutanja"));
		prilozi.setGeneralnoPpunomocje(createPrilog("generalnoPunomocjePutanja"));
		prilozi.setPunomocjeNaknadnoDostavljeno(createPrilog("punomocjeNaknadnoDostavljenoPutanja"));
		prilozi.setOpstiAkt(createPrilog("opstiAktPutanja"));
		prilozi.setDokazOPravuPrvenstva(createPrilog("dokazOPravuPrvenstvaPutanja"));
		prilozi.setDokazOUplatiTakse(createPrilog("dokazOUplatiTaksePutanja"));

		return prilozi;
	}

	private Prilog createPrilog(String putanja) {
		Prilog prilog = new Prilog();
		prilog.setSrc(putanja);

		return prilog;
	}

	private Taksa createTaksa1() {
		Taksa taksa = new Taksa();
		taksa.setTipTakse("osnovna taksa");
		taksa.setIznos(1200);
		taksa.setValuta("RSD");

		return taksa;
	}

	private Taksa createTaksa2() {
		Taksa taksa = new Taksa();
		taksa.setTipTakse("za graficko resenje");
		taksa.setIznos(2300);
		taksa.setValuta("RSD");

		return taksa;
	}

	private Zig createZig() {
		Zig zig = new Zig();
		zig.setTipZiga("individualni zig");

		zig.setZnak(createZnak());
		zig.getBrojeviKlasaRobe().add(2);
		zig.getBrojeviKlasaRobe().add(4);
		zig.setZatrazenoPravo("zatrazeno pravo");
		zig.setBrojPrijaveZiga(1);
		zig.setDatumPodnosenja(LocalDate.parse("2020-12-12"));

		return zig;
	}

	private Znak createZnak() {
		Znak znak = new Znak();
		znak.setTipZnaka("графички знак; боју, комбинацију боја");
		znak.setIzgledZnaka(createIzgledZnaka());
		znak.getBoje().add("crna");
		znak.getBoje().add("siva");
		znak.setOpisZnaka("latinicni");
		return znak;
	}

	private IzgledZnaka createIzgledZnaka() {
		IzgledZnaka izgledZnaka = new IzgledZnaka();
		izgledZnaka.setSrc("nekaRandomPutanja");

		return izgledZnaka;
	}

	private Lice createPunomocnik() {
		FizickoLice punomocnik = new FizickoLice();

		String email = "peraperic@gmail.com";
		String fax = "021";
		String telefon = "0632201310";
		punomocnik.setKontakt(createKontakt(email, fax, telefon));

		String drzava = "Srbija";
		String grad = "Novi Sad";
		int postanskiBroj = 21000;
		String ulica = "Rumenacki put";
		int broj = 118;
		punomocnik.setAdresa(createAdresa(drzava, grad, postanskiBroj, ulica, broj));
		punomocnik.setIme("Pera");
		punomocnik.setPrezime("Peric");

		return punomocnik;
	}

	public Lice createPodnosilac() {
		FizickoLice podnosilac = new FizickoLice();

		String email = "hurricane@gmail.com";
		String fax = "021";
		String telefon = "0652201310";
		podnosilac.setKontakt(createKontakt(email, fax, telefon));

		String drzava = "Srbija";
		String grad = "Novi Sad";
		int postanskiBroj = 21000;
		String ulica = "Rumenacki put";
		int broj = 5;
		podnosilac.setAdresa(createAdresa(drzava, grad, postanskiBroj, ulica, broj));
		podnosilac.setIme("Nikola");
		podnosilac.setPrezime("Sovilj");

		return podnosilac;
	}

	private Kontakt createKontakt(String email, String fax, String telefon) {
		Kontakt kontakt = new Kontakt();
		kontakt.setEmail(email);
		kontakt.setFax(fax);
		kontakt.setTelefon(telefon);

		return kontakt;
	}

	private Zavod createZavod() {
		Zavod zavod = new Zavod();
		zavod.setNaziv("Zavod za intelektualnu svojinu");

		String drzava = "Srbija";
		String grad = "Beograd";
		int postanskiBroj = 11000;
		String ulica = "Kneginje Ljubice";
		int broj = 5;
		zavod.setAdresa(createAdresa(drzava, grad, postanskiBroj, ulica, broj));

		return zavod;
	}

	private Adresa createAdresa(String drzava, String grad, int postanskiBroj, String ulica, int broj)
	{
		Adresa adresa = new Adresa();
		adresa.setDrzava(drzava);
		adresa.setMesto(grad);
		adresa.setPostanskiBroj(postanskiBroj);
		adresa.setUlica(ulica);
		adresa.setBroj(broj);

		return adresa;
	}


}
