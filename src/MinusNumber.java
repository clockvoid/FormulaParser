import java.math.BigDecimal;
import java.util.ArrayList;

public class MinusNumber  extends Operator {

	public MinusNumber(Expression arg0, Expression arg1) {
		setChildren(arg0, arg1);
	}
	
	@Override
	public String evaluate() {
		// TODO Auto-generated method stub
		ArrayList<Expression> children = getChildren();
		BigDecimal child1Decimal = new BigDecimal(children.get(0).evaluate());
		BigDecimal child2Decimal = new BigDecimal(children.get(1).evaluate());
		return child1Decimal.add(child2Decimal.negate()).toString();
	}

}
