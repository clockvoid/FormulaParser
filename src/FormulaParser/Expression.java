package FormulaParser;
import Exception.RuntimeErrorException;


public interface Expression {
	public String evaluate() throws RuntimeErrorException;
}
