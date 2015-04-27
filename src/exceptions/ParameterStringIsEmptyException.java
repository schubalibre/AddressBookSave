package exceptions;

public class ParameterStringIsEmptyException extends Exception {

	public ParameterStringIsEmptyException(String parameter) {
		super(parameter);
	}

}
