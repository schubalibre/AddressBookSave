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

public class AddressBook implements AddressBookInterface {

	/*
	 * AddressBook - Die Aufgabe der Klasse AddressBook ist es, Kontakte vom Typ ContactDetails 
	 * zu speichern, zu ändern, zu suchen und zu löschen. Dabei werden die Kontakte durch einen Schlüssel 
	 * in der Treemap gespeichert, der sich aus dem Namen, Nachnamen und der Telefonnummer ergibt.
	 * Damit die Originalität des Schlüssels gewährleistet wird, werden Namen, Nachnamen und Telefonnummer 
	 * durch einen doppelten Doppelpunkt getrennt. (hans::müller::123456789). 
	 * Dies ermöglicht uns auch direkt in den Schlüsseln zu suchen da der Schlüssel immer noch "Klardaten" enthält.
	 * Somit müssen die Kontakte nicht mehr doppelt gespeichert werden und es ist möglich Kontakte mit gleichen 
	 * Vornamen oder Nachnamen zu speichern. 
	 * 
	 * */

	/**
	 * Unsere Treemap die die Speicherung der Kontakte übernimmt
	 */
	private Map<String, ContactDetails> namesMap;
	
	
	/**
	 * Standardkonstruktor - erstellt unsere Treemap
	 */
	public AddressBook() {
		namesMap = new TreeMap<>();
	}
	
	/**
	 * Kopierkonstruktor - nimmt ein typgleiches Objekt über seinen Parameter entgegen und erstellt aus diesem Objekt die Startwerte für seinen eigenen Zustand
	 * @param obj
	 */
	public AddressBook(AddressBook obj) {
		namesMap = obj.namesMap;
	}
	
	/* (non-Javadoc)
	 * @see classes.AddressBookInterface#getDetails(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ContactDetails getDetails(String key) throws DetailsNotFoundException, ParameterStringIsEmptyException {
		
		ContactDetails[] matched = this.search(key);

		if (!(matched.length > 0))
			throw new DetailsNotFoundException("Leider konnten keine Kontakte mit dem Key '" + key + "' gefunden werden!");
		
		return  matched[0];
	}
	
	/* (non-Javadoc)
	 * @see classes.AddressBookInterface#getDetails(java.lang.String, java.lang.String, java.lang.String)
	 */
	public ContactDetails getDetails(String name, String lastname, String phone, String mail, String address) throws DetailsNotFoundException, ParameterStringIsEmptyException {
		// NEU - wir generieren unseren Key
		String key = this.generateKey(name, lastname, phone, mail, address);

		if (!this.keyInUse(key))
			throw new DetailsNotFoundException("Leider konnten keine Kontakte '" + name + ", " + lastname + ", " + phone + "' gefunden werden!");
		// wenn ein Eintrag bei namesMap existiert wird dieser zurück gegeben,
		// sonst null
		return namesMap.get(key);
	}

	/* (non-Javadoc)
	 * @see classes.AddressBookInterface#keyInUse(java.lang.String)
	 */
	@Override
	public boolean keyInUse(String key) throws ParameterStringIsEmptyException {
		// key wird hier aufbereitet - trim und toLowerCase
		key = this.getCleanParameter(key);
		// es wird geschaut, ob namesMap den key besitzt (true oder false)
		return namesMap.containsKey(key);
	}

	/* (non-Javadoc)
	 * @see classes.AddressBookInterface#addDetails(classes.ContactDetails)
	 */
	@Override
	public void addDetails(ContactDetails details) throws DuplicateKeyException, InvalidContactException, ParameterStringIsEmptyException{
		// wir überprüfen, ob details etwas enthält
		if (details != null) {
			// NEU - wir generieren unseren Key
			String key = this.generateKey(details);
			
			// Kontrolle ob der Key schon benutzt wird
			if (this.keyInUse(key))
				throw new DuplicateKeyException("Der Kontakt " + details.getVorname() +", " + details.getNachname() +" ist schon vorhanden.");
			
				// alles klar wir kreieren die zwei Elemente für unsere Map
				namesMap.put(key, details);
		}else{
			throw new InvalidContactException("Es sind leider keine Kontaktdaten vorhanden.");
		}
	}
	
	/* (non-Javadoc)
	 * @see classes.AddressBookInterface#changeDetails(java.lang.String, classes.ContactDetails)
	 */
	@Override
	public void changeDetails(String oldKey, ContactDetails details) throws DuplicateKeyException, InvalidContactException, KeyIsNotInUseException, ParameterStringIsEmptyException {
		
		// wir überprüfen, ob details etwas enthält
		if (details != null) {
			// oldKey wird hier aufbereitet: trim und toLowerCase
			oldKey = this.getCleanParameter(oldKey);
			
			if (!this.keyInUse(oldKey)) 
				throw new KeyIsNotInUseException("Leider konnten kein Kontakte mit dem Key '" + oldKey + "' gefunden werden!");
			// wenn der Eintrag existiert.....
				
			// löschen wir als erstes die alten Einträge mit Hilfe des alten Keys
			this.removeDetails(oldKey);

			// dann nutzten wir unsere Methode addDetails um den geänderten Kontakt neu einzutragen
			this.addDetails(details);
		
		}else{
			throw new InvalidContactException("Es sind leider keine Kontaktdaten vorhanden.");
		}
	}

	/* (non-Javadoc)
	 * @see classes.AddressBookInterface#search(java.lang.String)
	 */
	@Override
	public ContactDetails[] search(String keyPrefix) throws ParameterStringIsEmptyException, DetailsNotFoundException {
		// keyPrefix wird hier aufbereitet: trim und toLowerCase
		keyPrefix = this.getCleanParameter(keyPrefix);
		// wenn keyPrefix nicht leer ist
		if (keyPrefix.length() > 0) {
			// erstellen wir eine ArrayList, da wir noch nicht wissen wie viele Kontakte wir finden werden
			List<ContactDetails> matchedDetails = new ArrayList<ContactDetails>();
			// wir holen uns alle Keys
			for (String key : namesMap.keySet()) {
				// und wenn ein key die Suchparameter enthält
				if (key.contains(keyPrefix)) {
					//splitten sie in ihre einzelnen Bestandteile (hans::müller::123456789 -> hans, müller, 123456789) 
					String[] keyArray = key.split("::");
					// und holen uns die ContactDetails
					matchedDetails.add(this.getDetails(keyArray[0],keyArray[1],keyArray[2],keyArray[3],keyArray[4]));
				}
			}
			// wir geben ein Array mit all unseren Kontakten zurück
			return matchedDetails.toArray( new ContactDetails[matchedDetails.size()] );
		}
		// sonst nichts - es werden keine Exception geworfen, da es seien kann, dass nichts gefunden wurde
		return null;
	}
	
	/* (non-Javadoc)
	 * @see classes.AddressBookInterface#getAllContacts()
	 */
	@Override
	public ContactDetails[] getAllContacts() throws DetailsNotFoundException, ParameterStringIsEmptyException {
		// wir erstellen eine ArrayList, da wir noch nicht wissen wie viele Kontakte wir haben
		List<ContactDetails> matchedDetails = new ArrayList<ContactDetails>();
		// wir holen uns alle Keys
		for (String key : namesMap.keySet()) {
				//splitten sie in ihre einzelnen Bestandteile (hans::müller::123456789 -> hans, müller, 123456789) 
				String[] keyArray = key.split("::");
				// und holen uns die ContactDetails
				matchedDetails.add(this.getDetails(keyArray[0],keyArray[1],keyArray[2],keyArray[3],keyArray[4]));
		}
		// wir geben ein Array mit all unseren Kontakten zurück
		return matchedDetails.toArray( new ContactDetails[matchedDetails.size()] );
	}

	/* (non-Javadoc)
	 * @see classes.AddressBookInterface#getNumberOfEntries()
	 */
	@Override
	public int getNumberOfEntries() {
		// gibt die Größe der Liste zurück
		return namesMap.size();
	}

	/* (non-Javadoc)
	 * @see classes.AddressBookInterface#removeDetails(java.lang.String)
	 */
	@Override
	public void removeDetails(String key) throws KeyIsNotInUseException, ParameterStringIsEmptyException{
		// wir löschen aus beiden Maps die Einträge
		if (!this.keyInUse(key))
			throw new KeyIsNotInUseException("Der Kontakt " + key + " konnte nicht gefunden werden.");
			
			// und löschen mit name und lastName die Einträge aus Maps
			namesMap.remove(key);
	}

	/**
	 * getCleanParameter - säubert unser Parameter und gibt sie in Kleinbuchstaben zurück. Kontrolliert ob die Parameter nicht leer sind.
	 * @param key
	 * @return String
	 * @throws ParameterStringIsEmptyException
	 */
	private String getCleanParameter(String key) throws ParameterStringIsEmptyException{
		// Überprüfung ob die Parameter null/ leer sind
		if (key == null || key.isEmpty())
			throw new ParameterStringIsEmptyException("Leider ist der Parameter leer.");
		
			return key.trim().toLowerCase();
	}

	/* (non-Javadoc)
	 * @see classes.AddressBookInterface#generateKey(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String generateKey(String name, String lastname, String phone, String mail, String address) throws ParameterStringIsEmptyException{
		// wir säubern die Keys
		name = this.getCleanParameter(name);
		lastname =this.getCleanParameter(lastname);
		// wenn kein Telefon vorhanden ist wird eine Defaultnummer erstellt und somit die Speicherung ohne Telefon ermöglicht.
		phone = (phone.isEmpty()) ? "0000000000" : this.getCleanParameter(phone);
		mail = (mail.isEmpty()) ? "0000000000" : this.getCleanParameter(mail);
		address = (address.isEmpty()) ? "0000000000" : this.getCleanParameter(address);
		
		
		// Der Key wird durch den Namen + Nachnamen + Telefon generiert und somit eindeutig gemacht (separiert werden die Strings durch :: um die Eindeutigkeit zu gewähren).
		return name + "::" + lastname + "::" + phone + "::" + mail + "::" + address;
	}
	
	/* (non-Javadoc)
	 * @see classes.AddressBookInterface#generateKey(classes.ContactDetails)
	 */
	@Override
	public String generateKey(ContactDetails details) throws ParameterStringIsEmptyException{
		// gleiche Funktion wie oben nur nimmt stattdessen ein Objekt ContactDetails entgegen
		return this.generateKey(details.getVorname(), details.getNachname(), details.getTelefonnummer(), details.getMail(), details.getAdresse());
	}

}
