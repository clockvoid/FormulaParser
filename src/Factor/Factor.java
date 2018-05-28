package Factor;
import Exception.RuntimeErrorException;
import FormulaParser.Expression;


public abstract class Factor implements Expression {

	private Expression child;
	
	public Expression getChild() {
		return this.child;
	}
	
	public void setChild(Expression arg0) {
		this.child = arg0;
	}
	
	@Override
	abstract public String evaluate() throws RuntimeErrorException;

}
