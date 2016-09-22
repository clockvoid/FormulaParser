package Factor;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Exception.RuntimeErrorException;
import FormulaParser.Expression;
import FormulaParser.UnitParser;
import Operator.Operator;


public class PowUnit extends Operator {

	public PowUnit(Expression arg0, Expression arg1) {
		setChildren(arg0, arg1);
	}
	
	@Override
	public String evaluate() throws RuntimeErrorException {
		// TODO Auto-generated method stub
		List<Expression> children = getChildren();
		Map<String, String> unitMap = UnitParser.createUnitMap(children.get(0).evaluate());
		Map<String, String> newUnitMap = new HashMap<String, String>();
		String index = children.get(1).evaluate();
		for (String key : unitMap.keySet()) {
			newUnitMap.put(key, new BigDecimal(unitMap.get(key)).multiply(new BigDecimal(index)).toString());
		}
		return UnitParser.createUnitString(newUnitMap);
	}

}
