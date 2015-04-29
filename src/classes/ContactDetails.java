package classes;


public class ContactDetails {

	// Attribute

	private String vorname = null,nachname = null,adresse = null,telefonnummer = null, mail = null;

	// Konstruktoren
	
	// Standartkonstruktor
	public ContactDetails(){
		
	}
	
	// Kopierkonstruktor
	public ContactDetails(ContactDetails details){
		this.setVorname(details.getVorname());
		this.setNachname(details.getNachname());
		this.setTelefonnummer(details.getTelefonnummer());
		this.setMail(details.getMail());
		this.setAdresse(details.getAdresse());
	}
	
	public ContactDetails(String vorname, String nachname) {

		this(vorname, nachname, null, null, null);

	}

	public ContactDetails(String vorname, String nachname, String telefonnummer, String mail, String adresse){
		
		this.vorname = vorname;

		this.nachname = nachname;

		this.adresse = adresse;

		this.telefonnummer = telefonnummer;

		this.mail = mail;

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
	
	public void setVorname(String vorname) {

		this.vorname = vorname;

	}
	
	public void setNachname(String nachname) {

		this.nachname = nachname;

	}

	public void setAdresse(String adresse) {

		this.adresse = adresse;

	}

	public void setTelefonnummer(String telefonnummer) {

		this.telefonnummer = telefonnummer;

	}

	public void setMail(String mail) {

		this.mail = mail;

	}
	
	
	@Override
	public String toString() {
		return "ContactDetails [vorname=" + vorname + ", nachname=" + nachname
				+ ", adresse=" + adresse + ", telefonnummer=" + telefonnummer
				+ ", mail=" + mail + "]";
	}

	public static void main(String[] args) {
		ContactDetails a = new ContactDetails("Robert", "Müller");
		
		ContactDetails b = a;
		
		ContactDetails c = new ContactDetails(a);
		
		a.setVorname("Hans");
		
		System.out.println(a.toString());
		
		System.out.println(b.toString());
		
		System.out.println(c.toString());
	}

}