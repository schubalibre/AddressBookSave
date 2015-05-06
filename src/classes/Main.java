package classes;
import classes.ContactDetails;

import java.util.Arrays;


public class Main {
	

	
	public static void main(String[] args) {
		
		//testeContactDetails();
		//testeAddressBookDoppelterEintrag();
		//testeAddressBook();
		testeSearchMethode();
	}
	
	public static void testeContactDetails(){
		
		ContactDetails details = new ContactDetails("Robert", "Dziuba");
		//ContactDetails details = new ContactDetails("", ""); OPTIONAL FÃœR EmptyStringForSurnameOrLastname_Exception
		ContactDetails details2 = new ContactDetails("Robert", "Dziuba", "WurststraÃŸe 10", null, "robert.dziuba@web.de");
		ContactDetails details3 = new ContactDetails("Robert", "Dziuba", "WurststraÃŸe 10", null, null);
		ContactDetails details4 = new ContactDetails("Mia", "Peters", "MiapeterstraÃŸe 123", "", "");
		ContactDetails details5 = new ContactDetails("Hans", "Wurst", null, null, null);
		
		
		System.out.println(details.getVorname());
		System.out.println(details.getNachname());
		System.out.println(details.getTelefonnummer());
		System.out.println(details.getAdresse());
		System.out.println(details.getMail());
		System.out.println("-------------------------------------");
		
		
		System.out.println(details2.getVorname());
		System.out.println(details2.getNachname());
		System.out.println(details2.getTelefonnummer());
		System.out.println(details2.getAdresse());
		System.out.println(details2.getMail());
		System.out.println("-------------------------------------");
		
		System.out.println(details3.getVorname());
		System.out.println(details3.getNachname());
		System.out.println(details3.getTelefonnummer());
		System.out.println(details3.getAdresse());
		System.out.println(details3.getMail());
		System.out.println("-------------------------------------");
		
		System.out.println(details4.getVorname());
		System.out.println(details4.getNachname());
		System.out.println(details4.getTelefonnummer());
		System.out.println(details4.getAdresse());
		System.out.println(details4.getMail());
		System.out.println("-------------------------------------");
		
		
		System.out.println(details5.getVorname());
		System.out.println(details5.getNachname());
		System.out.println(details5.getTelefonnummer());
		System.out.println(details5.getAdresse());
		System.out.println(details5.getMail());
		System.out.println("-------------------------------------");
	}
	
	public static void teste1a() {
		AddressBook book1 = new AddressBook();
		AddressBookInterface book2 = new AddressBook();
		
	}
	
	//DublicateKeyException
	public static void testeAddressBookDoppelterEintrag(){
		
		ContactDetails details = new ContactDetails("Robert", "Dziuba");
		ContactDetails details2 = new ContactDetails("Robert", "Dziuba", "WurststraÃŸe 10", null, "robert.dziuba@web.de");
		ContactDetails details3 = new ContactDetails("Robert", "Dziuba", "WurststraÃŸe 10", null, null);
		
		AddressBook buch = new AddressBook();
		
		
		//buch.addDetails(details3);
		//buch.addDetails(details2);
		//buch.addDetails(details);
		
		System.out.println(buch.getNumberOfEntries());
		
	}
	
	public static void testeAddressBook(){
		
		ContactDetails details = new ContactDetails("Robert", "Dziuba");
		ContactDetails details2 = new ContactDetails("Tobias", "Klatt", "WurststraÃŸe 10", null, "tobias.klatt@web.de");
		ContactDetails details3 = new ContactDetails("Inga", "Schwarze", "WurststraÃŸe 10", null, null);
		
		AddressBook buch = new AddressBook();
		
		//Kontakte zum Addressbuch hinzufÃ¼gen
		//buch.addDetails(details3);
		//buch.addDetails(details2);
		//buch.addDetails(details);
		
		//PrÃ¼fe, wie viele EintrÃ¤ge vorhanden sind: 3
		System.out.println(buch.getNumberOfEntries()+ " EintrÃ¤ge vorhanden");
		
		
		//EintrÃ¤ge lÃ¶schen, Robert lÃ¶schen
		//buch.removeDetails("Robert");
		//Erneut Anzahl der EintrÃ¤ge Ã¼berprÃ¼fen: 2
		System.out.println(buch.getNumberOfEntries()+ " EintrÃ¤ge vorhanden");
	
		
		// Robert wieder hinzufÃ¼gen
		//buch.addDetails(details);
		//Erneut Anzahl der EintrÃ¤ge prÃ¼fen: 3
		System.out.println(buch.getNumberOfEntries()+ " EintrÃ¤ge vorhanden");
		System.out.println("-------------------------------------");
		
		//PrÃ¼fe, ob Robert vorhanden ist: true
		//System.out.println(buch.keyInUse("Robert"));
		System.out.println("-------------------------------------");
		
		//PrÃ¼fe, ob Dziuba vorhanden ist: true
		//System.out.println(buch.keyInUse("Dziuba"));
		System.out.println("-------------------------------------");
		
		//PrÃ¼fe, ob ein fremder Key vorhanden ist: false
		//System.out.println(buch.keyInUse("Hans"));
		System.out.println("-------------------------------------");
		
		//LÃ¶sche Robert und prÃ¼fe, ob Robert oder Dziuba vorhanden ist: false, false
		//buch.removeDetails("Robert");
		//System.out.println(buch.keyInUse("Robert"));
		System.out.println("-------------------------------------");
		//System.out.println(buch.keyInUse("Dziuba"));
		System.out.println("-------------------------------------");
		
		//FÃ¼ge Robert wieder hinzu und prÃ¼fe, ob Robert oder Dziuba vorhanden ist: true, true
		//buch.addDetails(details);
		//System.out.println(buch.keyInUse("Robert"));
		System.out.println("-------------------------------------");
		//System.out.println(buch.keyInUse("Dziuba"));
		System.out.println("-------------------------------------");
		
		//Gib alle Infos Ã¼ber Tobi, Robert, Inga und Ilse
		
		ContactDetails tobiDetails = null, ingaDetails = null, robertDetails = null, ilsesDetails = null;
		//try {
			//tobiDetails = buch.getDetails("Tobias");
			//ingaDetails = buch.getDetails("Inga");
			//robertDetails = buch.getDetails("Robert");
			//ilsesDetails = buch.getDetails("Ilse");
			
		//} catch (AddressBookException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}
		
		
		if(tobiDetails != null){
			System.out.println(tobiDetails.getVorname());
			System.out.println(tobiDetails.getNachname());
			System.out.println(tobiDetails.getMail());
			System.out.println(tobiDetails.getAdresse());
			System.out.println(tobiDetails.getTelefonnummer());
		}
	
		if(ingaDetails != null){
		System.out.println(ingaDetails.getVorname());
		System.out.println(ingaDetails.getNachname());
		System.out.println(ingaDetails.getMail());
		System.out.println(ingaDetails.getAdresse());
		System.out.println(ingaDetails.getTelefonnummer());
		}
		
		if(robertDetails != null){
		System.out.println(robertDetails.getVorname());
		System.out.println(robertDetails.getNachname());
		System.out.println(robertDetails.getMail());
		System.out.println(robertDetails.getAdresse());
		System.out.println(robertDetails.getTelefonnummer());
		}
		
		if(ilsesDetails != null){
		System.out.println(ilsesDetails.getVorname());
		System.out.println(ilsesDetails.getNachname());
		System.out.println(ilsesDetails.getMail());
		System.out.println(ilsesDetails.getAdresse());
		System.out.println(ilsesDetails.getTelefonnummer());
		}
		
		//Teste Methode change-Details
		//ContactDetails details6 = new ContactDetails("Karin", "Mueller", "SonntagstraÃŸe 25", "00123456789", "karinmueller@gmail.com");
		//buch.changeDetails("Robert", details6);
		//System.out.println(buch.keyInUse("Karin"));
		//System.out.println("-------------------------------------");
		
		//KeyStringIsEmptyException
		//try {
			//buch.getDetails("Einhorn");
		//} catch (AddressBookException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}
		
		
		
	}


	public static void testeSearchMethode() {
		
		ContactDetails details = new ContactDetails("Robert", "Dziuba");
		ContactDetails details2 = new ContactDetails("Andreas", "Austing", "Wurststrasse 10", null, "robert.dziuba@web.de");
		ContactDetails details3 = new ContactDetails("Inga", "Schwarze", "Tegeler Weg 107", null, null);
		
		AddressBook buch = new AddressBook();
		//buch.addDetails(details);
		//buch.addDetails(details2);
		//buch.addDetails(details3);
		
		
		
		//ContactDetails[] result = buch.search("Inga");
		
		//if(result.length != 1){
			//System.err.println("Ein Fehler ist aufgetreten.");
		//}else{
		//System.out.println(Arrays.toString(result));
		}
		
		
	}
		



