package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import classes.ContactDetails;

public class ContactDetailsTest {
	
	ContactDetails cd = new ContactDetails();
	
	@Before
	public void setUp() throws Exception {
		cd.setVorname("Hans");
		cd.setNachname("Müller");
		cd.setTelefonnummer("123456789");
		cd.setMail("hans@web.de");
		cd.setAdresse("Strasse 17");
	}

	@Test
	public void test() {
		assertEquals("Achtung ungleicher Vorname!", cd.getVorname(),"Hans");
	}

}
