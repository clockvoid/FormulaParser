package Factor;
import java.util.ArrayList;

import Exception.RuntimeErrorException;
import FormulaParser.Expression;
import Operator.Operator;


public class PowNumber extends Operator {

	public PowNumber(Expression arg0, Expression arg1) {
		setChildren(arg0, arg1);
	}
	
	@Override
	public String evaluate() throws RuntimeErrorException {
		// TODO Auto-generated method stub
		ArrayList<Expression> children = getChildren();
		double child1Decimal = Double.parseDouble(children.get(0).evaluate());
		double child2Decimal = Double.parseDouble(children.get(1).evaluate());
		return new Double(Math.pow(child1Decimal, child2Decimal)).toString();
	}

}
