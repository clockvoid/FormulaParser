package Operator;
import java.util.ArrayList;

import Exception.RuntimeErrorException;
import FormulaParser.Expression;


public abstract class Operator implements Expression {

	private Expression child1;
	private Expression child2;

	public ArrayList<Expression> getChildren() {
		ArrayList<Expression> children = new ArrayList<Expression>();
		children.add(child1);
		children.add(child2);
		return children;
	}
	
	public void setChildren(Expression arg0, Expression arg1) {
		this.child1 = arg0;
		this.child2 = arg1;
	}
	
	@Override
	abstract public String evaluate() throws RuntimeErrorException;

}
