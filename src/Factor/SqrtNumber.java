package Factor;
import Exception.RuntimeErrorException;
import FormulaParser.Expression;


public class SqrtNumber extends Factor {

	public SqrtNumber(Expression arg0) {
		setChild(arg0);
	}
	
	@Override
	public String evaluate() throws RuntimeErrorException {
		// TODO Auto-generated method stub
		double num = Double.parseDouble(getChild().evaluate());
		return Double.toString(Math.sqrt(num));
	}

}
