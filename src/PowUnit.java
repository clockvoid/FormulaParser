import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public class PowUnit extends Operator {

	public PowUnit(Expression arg0, Expression arg1) {
		setChildren(arg0, arg1);
	}
	
	@Override
	public String evaluate() {
		// TODO Auto-generated method stub
		List<Expression> children = getChildren();
		Map<String, String> unitMap = UnitParser.createUnitMap(children.get(0).evaluate());
		String index = children.get(1).evaluate();
		for (String key : unitMap.keySet()) {
			unitMap.replace(key, new BigDecimal(unitMap.get(key)).multiply(new BigDecimal(index)).toString());
		}
		return UnitParser.createUnitString(unitMap);
	}

}
