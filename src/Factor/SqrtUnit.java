package Factor;
import java.math.BigDecimal;
import java.util.Map;

import Exception.RuntimeErrorException;
import FormulaParser.Expression;
import FormulaParser.UnitParser;


public class SqrtUnit extends Factor {

	public SqrtUnit(Expression arg0) {
		setChild(arg0);
	}
	
	@Override
	public String evaluate() throws RuntimeErrorException {
		// TODO Auto-generated method stub
		Expression child = getChild();
		Map<String, String> unitMap = UnitParser.createUnitMap(child.evaluate());
		for (String key : unitMap.keySet()) {
			String value = new BigDecimal(unitMap.get(key)).divide(new BigDecimal("2")).toString();
			unitMap.replace(key, value);
		}
		return UnitParser.createUnitString(unitMap);
	}

}
