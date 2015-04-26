package classes;

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
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String generateKey(){
		name = this.getName();
		lastname = this.getName();
		phone = this.getName();
		return name + "::" + lastname + "::" + phone;
	}

}
