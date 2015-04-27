package classes;

import exceptions.EmptyKeyException;
import exceptions.ParameterStringIsEmptyException;

public class AddressBookKey {
	
	private String name, lastname, phone;
	
	public AddressBookKey(){
		
	}

	public AddressBookKey(String name, String lastname, String phone) throws ParameterStringIsEmptyException{
		this.setName(name);
		this.setLastname(lastname);
		this.setPhone(phone);
	}
	
	public AddressBookKey(ContactDetails details) throws ParameterStringIsEmptyException{
		this.setName(details.getVorname());
		this.setLastname(details.getNachname());
		this.setPhone(details.getTelefonnummer());
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) throws ParameterStringIsEmptyException {
		this.name = this.getCleanKey(name);
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) throws ParameterStringIsEmptyException {
		this.lastname = this.getCleanKey(lastname);
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) throws ParameterStringIsEmptyException {
		if(phone == null || phone.isEmpty()){
			phone = "0000000000";
		}
		
		this.phone = this.getCleanKey(phone);

	}
	
	public String generateKey(){
		name = this.getName();
		lastname = this.getLastname();
		phone = this.getPhone();
		return name + "::" + lastname + "::" + phone;
	}
	
	private String getCleanKey(String key) throws ParameterStringIsEmptyException {
		if (key == null || key.isEmpty())
			throw new ParameterStringIsEmptyException("Leider ist der Parameter leer.");
		return key.trim().toLowerCase();
	}

}
