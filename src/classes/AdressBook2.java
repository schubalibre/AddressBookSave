package classes;

import java.util.Map;
import java.util.TreeMap;

import exceptions.InkonsistenzException;
import exceptions.KeyIsInUseException;
import exceptions.KeyIsNotInUseException;
import exceptions.ParameterStringIsEmptyException;

public class AdressBook2 {

	// Attribute

	private Map<String, ContactDetails> namesMap = new TreeMap<String, ContactDetails>();

	// Methoden

	public ContactDetails getDetails(String key)
			throws ParameterStringIsEmptyException, KeyIsNotInUseException {

		if (keyInUse(key)) {

			return namesMap.get(key);

		} else {

			throw new KeyIsNotInUseException(key.toString());

		}

	}

	public boolean keyInUse(String key) throws ParameterStringIsEmptyException {

		key = this.getCleanParameter(key, "key");

		return namesMap.containsKey(key);

	}

	public void addDetails(ContactDetails details)
			throws ParameterStringIsEmptyException, KeyIsInUseException {

		String surname = this
				.getCleanParameter(details.getVorname(), "surname");

		String lastname = this.getCleanParameter(details.getNachname(),
				"lastname");

		if (keyInUse(surname)) {

			throw new KeyIsInUseException(surname.toString());

		}
		if (keyInUse(lastname)) {

			throw new KeyIsInUseException(lastname.toString());

		} else {

			namesMap.put(surname, details);

			namesMap.put(lastname, details);

		}

		try {
			inkonsistenz();
		} catch (InkonsistenzException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void changeDetails(String key, ContactDetails details)
			throws ParameterStringIsEmptyException, KeyIsInUseException {

		key = this.getCleanParameter(key, key.toString());

		String surname = this.getCleanParameter(details.getVorname(),
				details.getVorname());

		String lastname = this.getCleanParameter(details.getNachname(),
				details.getNachname());

		// if (keyInUse(lastname)) {

		// throw new KeyIsInUseException(lastname);

		// } if (keyInUse(surname)) {

		// throw new KeyIsInUseException(surname);

		// } else {

		// // this.removeDetails(key);

		// // this.addDetails(details);

		// }

	}

	public int getNumberOfEntries() {

		return namesMap.size() / 2;

	}

	public void removeDetails(String key)
			throws ParameterStringIsEmptyException, KeyIsNotInUseException {

		getCleanParameter(key, key.toString());

		if (keyInUse(key)) {

			ContactDetails oldDetails = getDetails(key);

			// oldDetails.getVorname();

			// namesMap.remove(oldDetails.getVorname());

			// namesMap.remove(oldDetails.getNachname());

			// namesMap.remove(this.getCleanParameter(oldDetails.getVorname(),
			// "TEST"));

			// namesMap.remove(this.getCleanParameter(oldDetails.getNachname(),
			// "TEST"));

			// inkonsistenz();

		} else {

			throw new KeyIsNotInUseException(key.toString());

		}

	}

	private String getCleanParameter(String key, String parameter)
			throws ParameterStringIsEmptyException {

		if (key == null || key == "") {

			throw new ParameterStringIsEmptyException(parameter);

		} else {

			return key.trim().toLowerCase();

		}

	}

	public ContactDetails[] search(String keyPrefix) {

		return null;

	}

	public void inkonsistenz() throws InkonsistenzException {

		if ((namesMap.size() % 2) != 0) {

			throw new InkonsistenzException();

		}

	}

}