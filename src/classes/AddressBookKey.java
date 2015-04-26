package classes;

import exceptions.EmptyKeyException;

public class AddressBookKey {
	
	private String name, lastname, phone;
	
	public AddressBookKey(){
		
	}

	public AddressBookKey(String name, String lastname, String phone){
		this.setName(name);
		this.setLastname(lastname);
		this.setPhone(phone);
	}
	
	public AddressBookKey(ContactDetails details){
		this.setName(details.getVorname());
		this.setLastname(details.getNachname());
		this.setPhone(details.getTelefonnummer());
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		try {
			this.name = this.getCleanKey(name);
		} catch (EmptyKeyException e) {
			System.out.println("name");
			e.printStackTrace();
		}
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		
		try {
			this.lastname = this.getCleanKey(lastname);
		} catch (EmptyKeyException e) {
			System.out.println("lastname");
			e.printStackTrace();
		}
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		if(phone == null || phone.isEmpty()){
			phone = "0000000000";
		}
		
		try {
			this.phone = this.getCleanKey(phone);
		} catch (EmptyKeyException e) {
			System.out.println("phone");
			e.printStackTrace();
		}
	}
	
	public String generateKey(){
		name = this.getName();
		lastname = this.getLastname();
		phone = this.getPhone();
		return name + "::" + lastname + "::" + phone;
	}
	
	private String getCleanKey(String key) throws EmptyKeyException {
		if (key == null || key.isEmpty())
			throw new EmptyKeyException("The key: " + key + " is empty");
		return key.trim().toLowerCase();
	}

}
