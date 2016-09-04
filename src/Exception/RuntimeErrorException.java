package Exception;

@SuppressWarnings("serial")
public class RuntimeErrorException extends Exception {

	private String message;
	
	public RuntimeErrorException(String arg0) {
		message = arg0;
	}
	
	public String getMessage() {
		return message;
	}
	
}
