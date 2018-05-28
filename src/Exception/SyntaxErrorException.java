package Exception;

@SuppressWarnings("serial")
public class SyntaxErrorException extends Exception {
	
	private String message;
	
	public SyntaxErrorException(String arg0) {
		message = arg0;
	}
	
	public String getMessage() {
		return message;
	}

}
