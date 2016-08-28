import java.math.BigDecimal;
import java.util.ArrayList;

public class DevideNumber extends Operator {

	public DevideNumber(Expression arg0, Expression arg1) {
		setChildren(arg0, arg1);
	}
	
	@Override
	public String evaluate() {
		// TODO Auto-generated method stub
		ArrayList<Expression> children = getChildren();
		BigDecimal child1Decimal = new BigDecimal(children.get(0).evaluate());
		BigDecimal child2Decimal = new BigDecimal(children.get(0).evaluate());
		return child1Decimal.divide(child2Decimal).toString();
	}

}
