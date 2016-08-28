import java.util.ArrayList;

public class MinusUnit extends Operator {

	public MinusUnit(Expression arg0, Expression arg1) {
		setChildren(arg0, arg1);
	}
	
	@Override
	public String evaluate() {
		// TODO Auto-generated method stub
		ArrayList<Expression> children = getChildren();
		String unit1 = children.get(0).evaluate();
		String unit2 = children.get(1).evaluate();
		if (unit1.equals(unit2)) {
			return unit1;
		} else {
			System.out.println("error!");
			return null;
		}
	}

}
