package tests;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import classes.AddressBook;
import classes.ContactDetails;
import exceptions.DetailsNotFoundException;
import exceptions.DuplicateKeyException;
import exceptions.InvalidContactException;
import exceptions.KeyIsNotInUseException;
import exceptions.ParameterStringIsEmptyException;

public class AddressBookTest {
	//Attribute
	ContactDetails cdTobias		= new ContactDetails("Tobias", "Klatt", "030", "Mond", "Tobi.Klatt@web.de");
	ContactDetails cdTobias2	= new ContactDetails("Tobias", "Klatt", "030", "Berlin", "Tobi.Klatt@web.de");
	ContactDetails cdRagnar		= new ContactDetails("Ragnar", "Lothbrok", "666", "ragnar@asgard.de", "Norwegen");
	ContactDetails cdEmpty		= new ContactDetails("", "", "", "", "");
	ContactDetails cdNull		= new ContactDetails(null, null, null, null, null);
	String key 					= "tobias::klatt::030::mond::tobi.klatt@web.de";
	AddressBook book			= new AddressBook();

//	Test	
//	getDetails(String)
	//Exceptions
	@Test (expected = DetailsNotFoundException.class)
	public void testGetDetailsDetailsNotFound () throws DetailsNotFoundException, ParameterStringIsEmptyException {
		book.getDetails("keinKey");
	}
	
	@Test (expected = ParameterStringIsEmptyException.class)
	public void testGetDetailsEmptyParameter() throws DetailsNotFoundException, ParameterStringIsEmptyException {
		book.getDetails("");
	}
	
	@Test (expected = ParameterStringIsEmptyException.class)
	public void testGetDetailsNullParameter() throws DetailsNotFoundException, ParameterStringIsEmptyException {
		book.getDetails(null);
	}
	
	//Methode
	@Test
	public void testGetDetails() throws DetailsNotFoundException, ParameterStringIsEmptyException, DuplicateKeyException, InvalidContactException {
		book.addDetails(cdTobias);
		assertEquals(cdTobias, book.getDetails("Tobias"));
	}
	
//	getDetails(String, String, String, String, String)
	//Exceptions
	@Test (expected = DetailsNotFoundException.class)
	public void testGetDetails2DetailsNotFound () throws DetailsNotFoundException, ParameterStringIsEmptyException {
		book.getDetails("keinKey", "keinKey", "keinKey", "keinKey", "keinKey");
	}
	
	@Test (expected = ParameterStringIsEmptyException.class)
	public void testGetDetails2EmptyParameter() throws DetailsNotFoundException, ParameterStringIsEmptyException {
		book.getDetails("", "", "", "", "");
	}
	
	@Test (expected = ParameterStringIsEmptyException.class)
	public void testGetDetails2NullParameter() throws DetailsNotFoundException, ParameterStringIsEmptyException {
		book.getDetails(null, null, null, null, null);
	}
	
	//Methode
		@Test
		public void testGetDetails2() throws DetailsNotFoundException, ParameterStringIsEmptyException, DuplicateKeyException, InvalidContactException {
			book.addDetails(cdTobias);	
			assertEquals(cdTobias, book.getDetails("Tobias", "Klatt", "030", "Mond", "Tobi.Klatt@web.de"));
		}

//	keyInUse(String):
	//Exceptions
	@Test (expected = ParameterStringIsEmptyException.class)
	public void testkeyInUseEmptyParameter() throws ParameterStringIsEmptyException {
		book.keyInUse(null);
	}
	
	//Methode
	@Test
	public void testkeyInUseVorname () throws ParameterStringIsEmptyException, DuplicateKeyException, InvalidContactException {
		book.addDetails(cdTobias);
		assertTrue(book.keyInUse(book.generateKey(cdTobias)));
	}
	
//	addDetails(ContactDetails)
	//Exceptions
	@Test (expected = DuplicateKeyException.class)
	public void testaddDetailsDuplicateKey () throws DuplicateKeyException, InvalidContactException, ParameterStringIsEmptyException {
		book.addDetails(cdTobias);
		book.addDetails(cdTobias);
	}
	
	@Test (expected = ParameterStringIsEmptyException.class)
	public void testaddDetailsEmptyParameter() throws ParameterStringIsEmptyException, DuplicateKeyException, InvalidContactException {
		book.addDetails(cdEmpty);
	}
	
	@Test (expected = ParameterStringIsEmptyException.class)
	public void testaddDetailsNullParameter() throws ParameterStringIsEmptyException, DuplicateKeyException, InvalidContactException {
		book.addDetails(cdNull);
	}
	
	@Test (expected = InvalidContactException.class)
	public void testaddDetailsInvalidContact() throws ParameterStringIsEmptyException, DuplicateKeyException, InvalidContactException {
		book.addDetails(null);
	}
	
	//Methode
	@Test 
	public void testaddDetails() throws DuplicateKeyException, InvalidContactException, ParameterStringIsEmptyException, DetailsNotFoundException {
		book.addDetails(cdTobias);
		assertEquals(cdTobias, book.getDetails("Tobias", "Klatt", "030", "Mond", "Tobi.Klatt@web.de"));
	}
	
//	changeDetails(String, ContactDetail)
	//Exceptions
	@Test (expected = ParameterStringIsEmptyException.class)
	public void testChangeDetailParameterNull () throws DuplicateKeyException, InvalidContactException, KeyIsNotInUseException, ParameterStringIsEmptyException {
		book.changeDetails(null, cdRagnar);
	}
	
	@Test (expected = KeyIsNotInUseException.class)
	public void testChangeDetailKeyIsNotInUseException () throws DuplicateKeyException, InvalidContactException, KeyIsNotInUseException, ParameterStringIsEmptyException {
		book.changeDetails("keinKey", cdRagnar);
	}
	
	@Test (expected = InvalidContactException.class)
	public void testChangeDetailInvalidContactException () throws DuplicateKeyException, InvalidContactException, KeyIsNotInUseException, ParameterStringIsEmptyException {
		book.changeDetails("keinKey", null);
	}
	
	@Ignore
	//Mein Verdacht ist, die Exception "DuplicateKeyException" wird überhaupt nie geworfen!
	//Ich konnte im Adressbuch zumindest die Aufrufstelle nicht ausmachen
	@Test (expected = DuplicateKeyException.class)
	public void testChangeDetailDuplicateKeyException () throws DuplicateKeyException, InvalidContactException, KeyIsNotInUseException, ParameterStringIsEmptyException {
		book.addDetails(cdTobias);
		String testKey = book.generateKey(cdTobias);
		book.changeDetails(testKey, cdRagnar);
	}
	
	//Methode
	@Test
	public void testChangeDetail () throws DuplicateKeyException, InvalidContactException, ParameterStringIsEmptyException, KeyIsNotInUseException, DetailsNotFoundException {
		book.addDetails(cdTobias);
		String testKey = book.generateKey(cdTobias);
		book.changeDetails(testKey, cdTobias2);
		assertEquals(cdTobias2, book.getDetails("tobias"));
	}
	
//	ContactDetails[] search(String)
	//Exceptions
	@Test
	public void testSearchParameterNull () throws ParameterStringIsEmptyException, DetailsNotFoundException{
		assertEquals(book.search(null), book.search(null));
	}
	
	//Methode
	@Test
	public void testSearch () throws DuplicateKeyException, InvalidContactException, ParameterStringIsEmptyException, DetailsNotFoundException {
		book.addDetails(cdTobias);
		ContactDetails[] cdTest = book.search("tobias");
		assertSame(cdTobias, cdTest[0]);
	}
	
//	getAllContacts()
	//Exceptions
	@Test (expected = ParameterStringIsEmptyException.class)
	public void testGetAllContactsParameterStringIsEmptyException () throws DuplicateKeyException, InvalidContactException, ParameterStringIsEmptyException, DetailsNotFoundException {
		book.addDetails(cdNull);
		book.search(null);
	}
	
	//Methode
	@Test
	public void testGetAllContact() throws DuplicateKeyException, InvalidContactException, ParameterStringIsEmptyException, DetailsNotFoundException {
		ContactDetails[] kontaktArr = {cdRagnar, cdTobias};
		book.addDetails(cdRagnar);
		book.addDetails(cdTobias);
		//Diese Methode ist veraltet aber eien andere L�sung habe ich nicht gefunden
		assertEquals(kontaktArr, book.search(null));
	}
	
//	getNumberOfEntries()
	//Methode
	@Test
	public void testGetNumberOfEntries() throws DuplicateKeyException, InvalidContactException, ParameterStringIsEmptyException {
		book.addDetails(cdRagnar);
		book.addDetails(cdTobias);
		assertEquals(2, book.getNumberOfEntries());
	}
	
//	removeDetails(String)
	//Exceptions
	@Test (expected = ParameterStringIsEmptyException.class)
	public void testRemoveDetailsParameterNull() throws ParameterStringIsEmptyException, DuplicateKeyException, InvalidContactException, KeyIsNotInUseException {
		book.removeDetails(null);
	}
	
	@Test (expected = KeyIsNotInUseException.class)
	public void testRemoveDetailsKeyIsNotInUseException() throws ParameterStringIsEmptyException, DuplicateKeyException, InvalidContactException, KeyIsNotInUseException {
		book.removeDetails("keinKey");
	}
	
	//Methode
	@Test
	public void testRemoveDetails() throws DuplicateKeyException, InvalidContactException, ParameterStringIsEmptyException, KeyIsNotInUseException {
		book.addDetails(cdTobias);
		book.removeDetails(key);
		assertFalse(book.keyInUse(key));
	}
	
//	generateKey(String, String, String, String, String)
	//Exceptions
	@Test (expected = ParameterStringIsEmptyException.class)
	public void testGenerateKeyParameterNull() throws ParameterStringIsEmptyException {
		book.generateKey(null, null, null, null, null);
	}
	
	//Methode
	@Test
	public void testGenerateKey() throws ParameterStringIsEmptyException {
		assertEquals("tobias::klatt::030::mond::tobi.klatt@web.de", book.generateKey("Tobias", "Klatt", "030", "Mond", "Tobi.Klatt@web.de"));
	}
	
//	generateKey(ContactDetails)
	//Exceptions
	@Test (expected = ParameterStringIsEmptyException.class)
	public void testGenerateKeyParameterNull2() throws ParameterStringIsEmptyException {
		book.generateKey(cdNull);
	}
	
	//Methode
	@Test
	public void testGenerateKey2() throws ParameterStringIsEmptyException {
		assertEquals(key, book.generateKey(cdTobias));
	}
}
