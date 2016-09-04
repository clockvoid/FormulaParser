package Operator;
import java.util.ArrayList;

import Exception.RuntimeErrorException;
import FormulaParser.Expression;

public class PluseUnit extends Operator {

	public PluseUnit(Expression arg0, Expression arg1) {
		setChildren(arg0, arg1);
	}
	
	@Override
	public String evaluate() throws RuntimeErrorException {
		// TODO Auto-generated method stub
		ArrayList<Expression> children = getChildren();
		String unit1 = children.get(0).evaluate();
		String unit2 = children.get(1).evaluate();
		if (unit1.equals(unit2)) {
			return unit1;
		} else {
			throw new RuntimeException("It does not match unit " + unit1 + " and " + unit2 + ".");
		}
	}

}
