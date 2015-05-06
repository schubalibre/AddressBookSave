package classes;
public class ContactDetails {
	//Attribute
	private String        vorname;
	private String        nachname;
	private String        adresse;
	private String        telefonnummer;
	private String        mail;
	 
		
		//Konstruktoren

		public ContactDetails(){
			//Standartkonstruktor
		}
		
		public ContactDetails(ContactDetails details){
			//Kopierkonstruktor, man sollte von Objekten auch Kopien machen dürfen
			this.setVorname(details.getVorname());
			this.setNachname(details.getNachname());
			this.setTelefonnummer(details.getTelefonnummer());
			this.setMail(details.getMail());
			this.setAdresse(details.getAdresse());
		}
		
		public ContactDetails(String vorname, String nachname) {
			this(vorname, nachname, null, null, null);
		}
		
		public ContactDetails(String vorname, String nachname, String telefonnummer, String mail, String adresse)  {
			
			
			this.vorname = vorname;
			this.nachname = nachname;
			this.adresse = adresse;
			this.telefonnummer = telefonnummer;
			this.mail = mail;
			
		}
				 
		//Getter
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
		 
		//Setter
		
		public void setAdresse(String Adresse) {
		this.adresse = Adresse;
		}
		 
		public void setTelefonnummer(String telefonnummer) {
		this.telefonnummer = telefonnummer;
		}
		 
		public void setMail(String mail) {
		this.mail = mail;
		}
		
		public void setVorname(String vorname) {
			this.vorname = vorname;
			}
		
		public void setNachname(String nachname) {
			this.nachname = nachname;
			}
		 
		public String toString() {
			return "ContactDetails [vorname=" + vorname + ", nachname="
					+ nachname + ", adresse=" + adresse + ", telefonnummer="
					+ telefonnummer + ", mail=" + mail + "]";
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