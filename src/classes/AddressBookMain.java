package classes;

import java.security.SecureRandom;
import java.util.Random;

import exceptions.DetailsNotFoundException;
import exceptions.DuplicateKeyException;
import exceptions.InvalidContactException;
import exceptions.KeyIsNotInUseException;
import exceptions.ParameterStringIsEmptyException;

public class AddressBookMain extends AddressBook {
	public static void main(String[] args) {
		System.out.println("1. AddressBook initialisieren....");
		AddressBook book = new AddressBook();
		System.out.println("1. AddressBook initialisiert....OK");

		System.out.println("2. AddressBook füllen....");
		for(int i = 0; i < 5; i++){
			ContactDetails a = new ContactDetails(
					csRandomAlphaNumericString(5), 
					csRandomAlphaNumericString(6),
					csRandomAlphaNumericString(9),
					csRandomAlphaNumericString(12),
					csRandomAlphaNumericString(25)
					);	
			try {
				book.addDetails(a);
				System.out.println("2. " + a.toString() + " erstellt.....OK");
			} catch (DuplicateKeyException | InvalidContactException | ParameterStringIsEmptyException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
		System.out.println("2. AddressBook gefüllt....OK");

		// Test - alle Kontakte auslesen
		
		System.out.println("3. hole alle Kontakte....");
		
		ContactDetails[] allContacts = null;
		
		try {
			allContacts = book.search(null);
		} catch (DetailsNotFoundException | ParameterStringIsEmptyException e) {
			System.out.println("Error: " + e.getMessage());
		}
		if(allContacts.length > 0){
			System.out.println("3. alle Kontakte geholt....OK");
		}else{
			System.out.println("3. Uppppssss keine Kontakte gefunden....FALSCH");
		}
		
		// Test - suche Kontakt
		
		System.out.println("4. Suche Kontakt mit Nachnamen '"+ allContacts[0].getNachname() +"'.");
		
		ContactDetails[] matched = null;
		
		try {
			matched = book.search(allContacts[0].getNachname());
		} catch (ParameterStringIsEmptyException | DetailsNotFoundException e) {
			System.out.println("4. Error: " + e.getMessage());
		}

		for(ContactDetails contact : matched){
			System.out.println("4. " + contact.toString());
		}
		
		if(matched.length == 1){
			System.out.println("4. Kontakt gefunden....OK");
		}else{
			System.out.println("4. Uppppssss kein/mehrere Kontakte gefunden....FALSCH");
		}

		// Test - lösche Kontakt
		
		System.out.println("5. lösche Kontakt '"+ allContacts[4].toString() +"'.");
		
		String key = "";
		try {
			key = book.generateKey(allContacts[4]);
		} catch (ParameterStringIsEmptyException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		try {
			book.removeDetails(key);
		} catch (KeyIsNotInUseException | ParameterStringIsEmptyException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		try {
			book.getDetails(allContacts[4].toString());
			System.out.println("5. Kontakt ist immer noch da....FALSCH");
		} catch (DetailsNotFoundException | ParameterStringIsEmptyException e1) {
			System.out.println("5. Gelöschter Kontakt '"+ allContacts[4].toString() +"'....OK");
		}
		
		// Test - verändere Kontakt
		
		System.out.println("6. verändere Kontakt mit Nachnamen 'aaaaaaaaaaaaa'.");
		
		String oldKey = null;
		try {
			oldKey = book.generateKey(allContacts[0]);
		} catch (ParameterStringIsEmptyException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		allContacts[0].setNachname("aaaaaaaaaaaaa");
		
		try {
			book.changeDetails(oldKey, allContacts[0]);
		} catch (DuplicateKeyException | InvalidContactException | KeyIsNotInUseException | ParameterStringIsEmptyException e) {
			System.out.println("Error: " + e.getMessage());;
		}
		
		ContactDetails contact = null;
		
		try {
			key = book.generateKey(allContacts[0]);
			contact = book.getDetails(key);
		} catch (ParameterStringIsEmptyException | DetailsNotFoundException e1) {
			e1.printStackTrace();
		}
		if(contact != null){
			if(contact.getNachname() == "aaaaaaaaaaaaa"){
				System.out.println("6. Kontakt verändert: "+ contact.toString() + ".....OK");
			}else{
				System.out.println("6. Uuupss Kontakt wurde nicht geändert....FALSCH");
			}
		}else{
			System.out.println("6. Uuupss Kontakt wurde nicht gefunden nach dem Ändern....FALSCH");
		}
		
		
		// Error tests

		System.out.println("7. erstelle doppelten Kontakt....");
		
		try {
			book.addDetails(allContacts[0]);
			System.out.println("7. Error: doppelter Eintrag!...... FALSCH");
		} catch (DuplicateKeyException | InvalidContactException | ParameterStringIsEmptyException e) {
			System.out.println("7. Error: " + e.getMessage() + "....OK");
		}
		
		
		System.out.println("8. lösche gelöschten Kontakt '"+ allContacts[4].toString() +"'.");
		
		try {
			key = book.generateKey(allContacts[4]);
		} catch (ParameterStringIsEmptyException e) {
			System.out.println("8. Error: " + e.getMessage() + "....FALSCH");
		}
		
		try {
			book.removeDetails(key);
			System.out.println("8. Error: Kontakt '"+ allContacts[4].toString() +"' nochmal gelöscht....... FALSCH");
		} catch (KeyIsNotInUseException | ParameterStringIsEmptyException e) {
			System.out.println("8. Error: " + e.getMessage() + "....OK");
		}
		
		
		ContactDetails errorDetails = new ContactDetails();
		
		System.out.println("9. erstelle leeren Kontakt.");
		
		try {
			book.addDetails(errorDetails);
			System.out.println("9. Error: leeren Kontakt erstellt!....FALSCH");
		} catch (DuplicateKeyException | InvalidContactException | ParameterStringIsEmptyException e) {
			System.out.println("9. Error: " + e.getMessage() + "....OK");
		}
		
		System.out.println("10. ändere leeren Kontakt.");
		
		try {
			book.changeDetails(oldKey, errorDetails);
			System.out.println("10. Error: leeren Kontakt geändert!....FALSCH");
		} catch (DuplicateKeyException | InvalidContactException | KeyIsNotInUseException | ParameterStringIsEmptyException e) {
			System.out.println("10. Error: " + e.getMessage() + "....OK");
		}
		
		
		System.out.println("11. lösche leeren Kontakt.");
		
		try {
			book.removeDetails(null);
			System.out.println("11. Error: leeren Kontakt gelöscht!....FALSCH");
		} catch (KeyIsNotInUseException | ParameterStringIsEmptyException e) {
			System.out.println("11. Error: " + e.getMessage() + "....OK");
		}
		
		errorDetails.setVorname("Robert");
		errorDetails.setTelefonnummer("030");
		
		System.out.println("12. erstelle unvollständigen Kontakt.");
		
		try {
			book.addDetails(errorDetails);
			System.out.println("12. Error: unvollständigen Kontakt erstellt!....FALSCH");
		} catch (DuplicateKeyException | InvalidContactException | ParameterStringIsEmptyException e) {
			System.out.println("12. Error: " + e.getMessage() + "....OK");
		}
		
		System.out.println("13. ändere unvollständigen Kontakt.");
		
		try {
			oldKey = book.generateKey(allContacts[2]);
		} catch (ParameterStringIsEmptyException e) {
			System.out.println("13. Error: " + e.getMessage() + "....OK");
		}
		
		try {
			System.out.println("13. Mein Kontakt: " + book.getDetails(oldKey));
		} catch (DetailsNotFoundException | ParameterStringIsEmptyException e) {
			System.out.println("13. Error: " + e.getMessage() + "....OK");
		}
		
		try {
			book.changeDetails(oldKey, errorDetails);
			System.out.println("13. Error: unvollständigen Kontakt geändert!....FALSCH");
		} catch (DuplicateKeyException | InvalidContactException | KeyIsNotInUseException | ParameterStringIsEmptyException e) {
			System.out.println("13. Error: " + e.getMessage() + "....OK");
		}
		
		System.out.println("14. alles nochvollständig?");
		
		try {
			System.out.println("14. " + book.getDetails(oldKey) + " ALLES OK");
		} catch (DetailsNotFoundException | ParameterStringIsEmptyException e) {
			System.out.println("14. Error: " + e.getMessage() + "....OK");
		}
	}
	
	
	//DefaultNamen, damit wir Zufallskontakte entwickeln können
	private static char[] VALID_CHARACTERS =
		    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456879".toCharArray();
	
	private static String csRandomAlphaNumericString(int numChars) {
		SecureRandom srand = new SecureRandom();
	    Random rand = new Random();
	    char[] buff = new char[numChars];

	    for (int i = 0; i < numChars; ++i) {
	      // reseed rand once you've used up all available entropy bits
	      if ((i % 10) == 0) {
	          rand.setSeed(srand.nextLong()); // 64 bits of random!
	      }
	      buff[i] = VALID_CHARACTERS[rand.nextInt(VALID_CHARACTERS.length)];
	    }
	    return new String(buff);
	}	
}
