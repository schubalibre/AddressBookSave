package interfaces;
import classes.AddressBookKey;
import classes.ContactDetails;

public interface AddressBookInterface {
	
	public ContactDetails getDetails(AddressBookKey key);
	
	public boolean keyInUse(AddressBookKey key);
	
	public void addDetails(ContactDetails details);
	
	public void changeDetails(AddressBookKey oldKey, ContactDetails details);
	
	public ContactDetails[] search(String keyPrefix);
	
	public int getNumberOfEntries();
	
	public void removeDetails(AddressBookKey key);
}
