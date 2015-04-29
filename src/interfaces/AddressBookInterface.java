package interfaces;

import classes.ContactDetails;
import exceptions.DetailsNotFoundException;
import exceptions.DuplicateKeyException;
import exceptions.InvalidContactException;
import exceptions.KeyIsNotInUseException;
import exceptions.ParameterStringIsEmptyException;

public interface AddressBookInterface {
	
	/**
	 * getDetails - Sucht Key und gibt falls vorhanden den ersten ContactDetails zurück
	 * @param key
	 * @return ContactDetails
	 * @throws DetailsNotFoundException
	 * @throws ParameterStringIsEmptyException
	 */
	public abstract ContactDetails getDetails(String key) throws DetailsNotFoundException,
			ParameterStringIsEmptyException;

	/**
	 * getDetails - Bildet aus allen übergebenen Parameter einen Key und gibt falls vorhanden einen ContactDetails zurück
	 * @param name
	 * @param lastname
	 * @param phone
	 * @return ContactDetails
	 * @throws DetailsNotFoundException
	 * @throws ParameterStringIsEmptyException
	 */
	public abstract ContactDetails getDetails(String name, String lastname,
			String phone, String mail, String address) throws DetailsNotFoundException,
			ParameterStringIsEmptyException;

	/**
	 * keyInUse - kontrolliert ob Key in Treemap (namesMap) enthalten ist
	 * @param key
	 * @return boolean
	 * @throws ParameterStringIsEmptyException
	 */
	public abstract boolean keyInUse(String key)
			throws ParameterStringIsEmptyException;

	/**
	 * addDetails - fügt einen ContactDetails in unsere Treemap (namesMap) hinzu
	 * @param details
	 * @throws DuplicateKeyException
	 * @throws InvalidContactException
	 * @throws ParameterStringIsEmptyException
	 */
	public abstract void addDetails(ContactDetails details)
			throws DuplicateKeyException, InvalidContactException,
			ParameterStringIsEmptyException;

	/**
	 * changeDetails - ändert einen vorhandenen Kontakt in den übergebenen Kontakt 
	 * @param oldKey
	 * @param details
	 * @throws DuplicateKeyException
	 * @throws InvalidContactException
	 * @throws KeyIsNotInUseException
	 * @throws ParameterStringIsEmptyException
	 */
	public abstract void changeDetails(String oldKey, ContactDetails details)
			throws DuplicateKeyException, InvalidContactException,
			KeyIsNotInUseException, ParameterStringIsEmptyException;

	/**
	 * search - such mit Hilfe des keyPrefix in den keys der Treemap und gibt ein ContactDetails Arrray mit allen gefundenen Kontakten zurück
	 * @param keyPrefix
	 * @return ContactDetails[]
	 * @throws ParameterStringIsEmptyException
	 */
	public abstract ContactDetails[] search(String keyPrefix)
			throws ParameterStringIsEmptyException, DetailsNotFoundException;

	/**
	 * getAllContacts - eine Methode die uns auf einen Schlag alle Kontakte zurückgibt
	 * @return ContactDetails[]
	 * @throws DetailsNotFoundException
	 * @throws ParameterStringIsEmptyException
	 */
	public abstract ContactDetails[] getAllContacts()
			throws DetailsNotFoundException, ParameterStringIsEmptyException;

	/**
	 * getNumberOfEntries - gibt die Menge unsere Einträge zurück
	 * @return int
	 */
	public abstract int getNumberOfEntries();

	/**
	 * removeDetails - löscht einen spezifischen Kontakt aus der Treemap mit Hilfe des übergenbenen Keys
	 * @param key
	 * @throws KeyIsNotInUseException
	 * @throws ParameterStringIsEmptyException
	 */
	public abstract void removeDetails(String key)
			throws KeyIsNotInUseException, ParameterStringIsEmptyException;

	/**
	 * generateKey - erstellt einen Key aus den übergebenen Parametern name, lastname, phone
	 * @param name
	 * @param lastname
	 * @param phone
	 * @return String
	 * @throws ParameterStringIsEmptyException
	 */
	public abstract String generateKey(String name, String lastname,
			String phone, String mail, String address) throws ParameterStringIsEmptyException;

	/**
	 * generateKey - gleiche Methode nur mit dem Parameter ContactDetails
	 * @param details
	 * @return String
	 * @throws ParameterStringIsEmptyException
	 */
	public abstract String generateKey(ContactDetails details)
			throws ParameterStringIsEmptyException;

}