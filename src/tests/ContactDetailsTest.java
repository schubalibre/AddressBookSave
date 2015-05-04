package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import classes.ContactDetails;


public class ContactDetailsTest {
	ContactDetails ragnar = new ContactDetails("Ragnar", "Lothbrok", "666", "ragnar@asgard.de", "Norwegen");
	ContactDetails cd;
	
	@Before
	public void setUp() throws Exception {
		cd = new ContactDetails("Ragnar", "Lothbrok", "666", "ragnar@asgard.de", "Norwegen");
	}

	@Test
	public void testKonstruktor() {
		assertNotEquals("testKonstruktor", ragnar, cd);
	}
	
	@Test
	public void testGetVorname() {
//		String vorname = cd.getVorname();
//		assertEquals("testGetVorname", "Ragnar", "Ragnar");
//		assertEquals("testGetVorname", "ragnar".toLowerCase(), "Ragnar".toLowerCase());
		assertEquals("testGetVorname", "ragnar", cd.getVorname().toLowerCase());
		
	}

}
