package classes;

public class Main {
	
	private static AddressBook myBook = new AddressBook();

	public static void main(String[] args) {
		
		fillMyBook();
		System.out.println(myBook.getNumberOfEntries() + " Einträge");
		System.out.println("---------------------------------------------");
		
		ContactDetails[] details = new ContactDetails[10];
		
		details = myBook.search("Ber");
		if(details.length > 0){
			for(ContactDetails detail : details){
				//System.out.println(detail.getVorname());
			}
		}
//		getMyBookDetails();
//		System.out.println("---------------------------------------------");
//		changeMyBookDetails();
//		System.out.println("---------------------------------------------");
//		myBook.removeDetails("Roberto");
//		System.out.println(myBook.getNumberOfEntries() + " Einträge");
		
	}

	private static void changeMyBookDetails() {
		
		ContactDetails a = myBook.getDetails("Robert");
		
		System.out.println(a.getVorname() +", "+ a.getNachname() +", "+ a.getTelefonnummer() +", "+ a.getMail());
		
		ContactDetails details = new ContactDetails("Roberto", "Dziubao", "123456789","12345@web.de","Sonntagstr. 27");
		
		myBook.changeDetails("Robert", details);
		
		a = myBook.getDetails("Roberto");
		
		System.out.println(a.getVorname() +", "+ a.getNachname() +", "+ a.getTelefonnummer() +", "+ a.getMail());
		
		a = myBook.getDetails("Dziubao");
		
		System.out.println(a.getVorname() +", "+ a.getNachname() +", "+ a.getTelefonnummer() +", "+ a.getMail());
		
		a = myBook.getDetails("Dziuba");
		
		if(a != null)
			System.out.println(a.getVorname() +", "+ a.getNachname() +", "+ a.getTelefonnummer() +", "+ a.getMail());
	}

	private static void getMyBookDetails() {
		
		ContactDetails a = myBook.getDetails("Robert");
		
		System.out.println(a.getVorname() +", "+ a.getNachname());
		
		ContactDetails b = myBook.getDetails("Dziuba");
		
		System.out.println(b.getVorname() +", "+ b.getNachname());
		
		ContactDetails c = myBook.getDetails("Deuter");
		
		System.out.println(c.getVorname() +", "+ c.getNachname() +", "+ c.getTelefonnummer() +", "+ c.getMail());
		
	}

	private static void fillMyBook() {
		
		ContactDetails a = new ContactDetails("Robert", "Dziuba");
		
		myBook.addDetails(a);
		
		ContactDetails b = new ContactDetails("Bert", "Berman");
		
		a.setTelefonnummer("123456789");
		
		myBook.addDetails(b);
		
		ContactDetails c = new ContactDetails("Claudia", "Clorens");
		
		c.setTelefonnummer("123456789");
		c.setMail("12345@web.de");
		
		myBook.addDetails(c);
		
		ContactDetails d = new ContactDetails("Detlef", "Deuter");
		
		d.setTelefonnummer("123456789");
		d.setMail("12345@web.de");
		d.setAdresse("Sonntagstr. 27");
		
		myBook.addDetails(d);
		
		//System.out.println(myBook.keyInUse(null));
		
		
//		ContactDetails e = new ContactDetails(" ", " ");
//		
//		e.setTelefonnummer("123456789");
//		e.setMail("12345@web.de");
//		e.setAdresse("Sonntagstr. 27");
//		
//		myBook.addDetails(e);
//		
//		ContactDetails f = new ContactDetails(null, null);
//		
//		f.setTelefonnummer("123456789");
//		f.setMail("12345@web.de");
//		f.setAdresse("Sonntagstr. 27");
//		
//		myBook.addDetails(f);
		
	}
	
	

}
