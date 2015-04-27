package classes;

import interfaces.AddressBookInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import exceptions.DetailsNotFoundException;
import exceptions.DuplicateKeyException;
import exceptions.InvalidContactException;
import exceptions.KeyIsNotInUseException;
import exceptions.ParameterStringIsEmptyException;

public class AddressBook /*implements AddressBookInterface*/ {

	/*
	 * Die Idee des AddressBook ist es Kontakte mit zwei Schlüsseln zu
	 * speichern. Damit wir dies bewerkstelligen können speichern wir die
	 * Kontaktdaten zwei mal in namesMap ab. Somit kann unter dem Namen und
	 * unter dem Nachnamen gesucht werden.
	 * 
	 * robert -> Robert, Dziuba, 123456789, ..... dziuba -> Robert, Dziuba,
	 * 123456789, .....
	 * 
	 * Immer daran denken das man die ganze Maschine von außen nicht sieht ;)
	 */

	// eine Maps für name und lastName
	private Map<String, ContactDetails> namesMap;
	
	public AddressBook() {
		namesMap = new TreeMap<>();
	}
	
	public AddressBook(AddressBook obj) {
		namesMap = obj.namesMap;
	}
	
	
	public ContactDetails getDetails(String name, String lastname, String phone) throws DetailsNotFoundException, ParameterStringIsEmptyException {
		
		String key = this.generateKey(name, lastname, phone);

		if (!this.keyInUse(key))
			throw new DetailsNotFoundException("Leider konnten keine Kontakte '" + name + ", " + lastname + ", " + phone + "' gefunden werden!");
		// wenn ein Eintrag bei namesMap existiert wird dieser zurück gegeben,
		// sonst null
		return namesMap.get(key);
	}

	
	public boolean keyInUse(String key) throws ParameterStringIsEmptyException {
		// key wird hier aufbereitet trim toLowerCase
		key = this.getCleanKey(key);
		// es wird geschaut, ob namesMap den key besitzt (true oder false)
		return namesMap.containsKey(key);
	}

	
	public void addDetails(ContactDetails details) throws DuplicateKeyException, InvalidContactException, ParameterStringIsEmptyException{
		
		if (details != null) {

			String key = this.generateKey(details);
			
			// Kontrolle ob der name oder lastName schon benutzt wird
			if (this.keyInUse(key))
				throw new DuplicateKeyException("Der Kontakt " + details.getVorname() +", " + details.getNachname() +" ist schon vorhanden.");
				// alles klar wir kreieren die zwei Elemente für unsere Map
				namesMap.put(key, details);
		}else{
			throw new InvalidContactException("Es sind leider keine Kontaktdaten vorhanden.");
		}
	}
	
	
	public void changeDetails(String oldKey, ContactDetails details) throws DuplicateKeyException, InvalidContactException, KeyIsNotInUseException, ParameterStringIsEmptyException {
		// oldKey wird hier aufbereitet trim toLowerCase
		oldKey = this.getCleanKey(oldKey);
		// wenn der Eintrag existiert.....
		if (this.keyInUse(oldKey) && details!= null) {
			// wir löschen als erstes die alten Einträge
			this.removeDetails(oldKey);

			// dann nutzten wir unsere Methode addDetails um den geänderten
			// Eintrag neu einzutragen
			this.addDetails(details);

			/*
			 * WICHTIG! um unsere Daten konsistent zu halten verändern wir sie
			 * nicht sondern löschen die alten Einträge erst und kreieren dann
			 * den neuen Eintrag neu. Damit bleiben beide Maps konsistent
			 */
		}
	}

	
	public ContactDetails[] search(String keyPrefix) throws ParameterStringIsEmptyException {

		keyPrefix = this.getCleanKey(keyPrefix);

		if (keyPrefix.length() > 0) {
			List<ContactDetails> matchedDetails = new ArrayList<ContactDetails>();
			for (String key : namesMap.keySet()) {
				if (key.contains(keyPrefix)) {
					String[] keyArray = key.split("::");
					matchedDetails.add(this.getDetails(keyArray[0],keyArray[1],keyArray[2]));
				}
			}
			return matchedDetails.toArray( new ContactDetails[matchedDetails.size()] );
		}
		return null;
	}
	
	public ContactDetails[] getAllContacts() throws DetailsNotFoundException, ParameterStringIsEmptyException {
		
		List<ContactDetails> matchedDetails = new ArrayList<ContactDetails>();
		for (String key : namesMap.keySet()) {
				String[] keyArray = key.split("::");
				matchedDetails.add(this.getDetails(keyArray[0],keyArray[1],keyArray[2]));
		}
		return matchedDetails.toArray( new ContactDetails[matchedDetails.size()] );
	}

	
	public int getNumberOfEntries() {
		// gibt die größe der Liste zurück
		return namesMap.size();
	}

	
	public void removeDetails(String key) throws KeyIsNotInUseException, ParameterStringIsEmptyException{
		// wir löschen aus beiden Maps die Einträge
		if (!this.keyInUse(key))
			throw new KeyIsNotInUseException("Der Kontakt " + key + " konnte nicht gefunden werden.");
			
			// und löschen mit name und lastName die Einträge aus Maps
			namesMap.remove(key);
		
	}

	private String getCleanKey(String key) throws ParameterStringIsEmptyException{
		if (key == null || key.isEmpty())
			throw new ParameterStringIsEmptyException("Leider ist der Parameter leer.");
		
			return key.trim().toLowerCase();
	}
	
	public String toString(){
		
		String allContacts = null;
		for(String key:namesMap.keySet()) {
			allContacts = allContacts + namesMap.get(key).toString();
		}
		
		return allContacts;
	}
	
	public String generateKey(String name, String lastname, String phone) throws ParameterStringIsEmptyException{
		
		name = this.getCleanKey(name);
		lastname =this.getCleanKey(lastname);
		phone = (phone.isEmpty()) ? "0000000000" : this.getCleanKey(phone);
		
		return name + "::" + lastname + "::" + phone;
	}
	
	public String generateKey(ContactDetails details) throws ParameterStringIsEmptyException{
		
		String name = this.getCleanKey(details.getVorname());
		String lastname = this.getCleanKey(details.getNachname());
		String phone = (details.getTelefonnummer().isEmpty()) ? "0000000000": this.getCleanKey(details.getTelefonnummer());
		
		return name + "::" + lastname + "::" + phone;
	}

}
