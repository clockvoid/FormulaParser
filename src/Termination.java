
public abstract class Termination implements Expression {

	 private String value;
	 
	 public void setValue(String arg0) {
		 value = arg0;
	 }
	 
	 public String getValue() {
		 return value;
	 }
	
	@Override
	abstract public String evaluate(); 

}
