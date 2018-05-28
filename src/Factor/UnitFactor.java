package Factor;
import Exception.RuntimeErrorException;
import FormulaParser.Expression;


public class UnitFactor extends Factor {

	private Expression outSideUnit;
	
	public UnitFactor(Expression arg0, Expression arg1) {
		setChild(arg0);
		outSideUnit = arg1;
	}
	
	@Override
	public String evaluate() throws RuntimeErrorException {
		// TODO Auto-generated method stub
		if (getChild().evaluate().equals("void^0")) {
			return outSideUnit.evaluate();
		} else {
			throw new RuntimeErrorException("A unit that is in brackets must be void.");
		}
	}

}
