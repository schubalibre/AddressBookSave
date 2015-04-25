package classes;
import exceptions.EmptyStringForSurnameOrLastname_Exception;


public class ContactDetails {

	// Attribute

	private String vorname = null,nachname = null,adresse = null,telefonnummer = null, mail = null;

	// Konstruktoren

	public ContactDetails(String vorname, String nachname, String adresse, String telefonnummer, String mail) throws EmptyStringForSurnameOrLastname_Exception{
		
		if( vorname.isEmpty() || nachname.isEmpty() )
			throw new EmptyStringForSurnameOrLastname_Exception();

		this.vorname = vorname;

		this.nachname = nachname;

		this.adresse = adresse;

		this.telefonnummer = telefonnummer;

		this.mail = mail;

	}

	public ContactDetails(String vorname, String nachname) {

		this(vorname, nachname, null, null, null);

	}

	// Getter

	public String getNachname() {

		return nachname;

	}

	public String getVorname() {

		return vorname;

	}

	public String getAdresse() {

		return adresse;

	}

	public String getTelefonnummer() {

		return telefonnummer;

	}

	public String getMail() {

		return mail;

	}

	// Setter

	public void setAdresse(String Adresse) {

		this.adresse = Adresse;

	}

	public void setTelefonnummer(String telefonnummer) {

		this.telefonnummer = telefonnummer;

	}

	public void setMail(String mail) {

		this.mail = mail;

	}

}