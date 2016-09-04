package Operator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import Exception.RuntimeErrorException;
import FormulaParser.Expression;
import FormulaParser.UnitParser;


public class DivideUnit extends Operator {

	public DivideUnit(Expression arg0, Expression arg1) {
		setChildren(arg0, arg1);
	}
	
	@Override
	public String evaluate() throws RuntimeErrorException {
		// TODO Auto-generated method stub
		ArrayList<Expression> children = getChildren();
		Map<String, String> unitMap1 = UnitParser.createUnitMap(children.get(0).evaluate());
		Map<String, String> unitMap2 = UnitParser.createUnitMap(children.get(1).evaluate());
		Map<String, String> bondedUnit = new HashMap<String, String>();
		Set<String> keySet = unitMap1.keySet();
		for (String str : keySet) {
			String tmp = unitMap2.get(str);
			if (tmp != null) {
				String num = new BigDecimal(tmp).add(new BigDecimal(unitMap1.get(str)).negate()).toString();
				unitMap1.remove(str);
				unitMap2.remove(str);
				bondedUnit.put(str, num);
			}
		}
		bondedUnit.putAll(unitMap1);
		bondedUnit.putAll(unitMap2);
		return UnitParser.createUnitString(bondedUnit);
	}

}
