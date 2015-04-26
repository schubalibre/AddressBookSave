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
		if(phone.isEmpty()){
			phone = "0000000000";
		}
		this.phone = phone;
	}
	
	public String getKey(){
		return  getCleanKey(this.getName()) + "::" + getCleanKey(this.getLastname()) + "::" + getCleanKey(this.getPhone());
	}
	
	private String getCleanKey(String key) {
		if (key != null && !key.isEmpty()) {
			return key.trim().toLowerCase();
		}
		return key;
	}
	
	
}
