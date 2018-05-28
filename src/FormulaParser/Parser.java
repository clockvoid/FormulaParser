package FormulaParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import Exception.SyntaxErrorException;
import Factor.PowNumber;
import Factor.PowUnit;
import Factor.SqrtNumber;
import Factor.SqrtUnit;
import Factor.UnitFactor;
import Operator.DivideNumber;
import Operator.DivideUnit;
import Operator.MinusNumber;
import Operator.MinusUnit;
import Operator.PluseNumber;
import Operator.PluseUnit;
import Operator.TimeNumber;
import Operator.TimeUnit;
import Termination.Number;
import Termination.Unit;

public class Parser {
	
	private ArrayList<String> program;
	private ListIterator<String> programCounter;
	
	public Parser(String arg0) {
		this.program = new ArrayList<String>(Arrays.asList(arg0.split("\\s+")));
	}
	
	public void setProgram(String arg0) {
		this.program = new ArrayList<String>(Arrays.asList(arg0.split("\\s+")));
	}
	
	public Expression compile() throws SyntaxErrorException {
		this.programCounter = program.listIterator();
		List<Expression> array = createExpression();
		Expression treeRoot = new TreeRoot(array.get(0), array.get(1));
		return treeRoot;
	}
	
	private List<Expression> createNumber() throws SyntaxErrorException {
		String str = programCounter.next();
		String[] array = str.split("\\[");
		List<String> parsedUnit;
		if (array.length == 1) {
			parsedUnit = UnitParser.evaluatePrefix(str, "void^0");
		} else {
			parsedUnit = UnitParser.evaluatePrefix(array[0], array[1].replaceAll("\\]", ""));
		}
		Expression n = new Number(parsedUnit.get(0));
		Expression u = new Unit(parsedUnit.get(1));
		List<Expression> ans = new ArrayList<Expression>();
		ans.add(n);
		ans.add(u);
		return ans;
	}
	
	private List<Expression> createExpression() throws SyntaxErrorException {
		List<Expression> x = createTerm();
		while (programCounter.hasNext()) {
			String str = programCounter.next();
			if (str.equals("+")) {
				List<Expression> t = createTerm();
				List<Expression> e = new ArrayList<Expression>();
				e.add(new PluseNumber(x.get(0), t.get(0)));
				e.add(new PluseUnit(x.get(1), t.get(1)));
				x = e;
			} else if (str.equals("-")) {
				List<Expression> t = createTerm();
				List<Expression> e = new ArrayList<Expression>();
				e.add(new MinusNumber(x.get(0), t.get(0)));
				e.add(new MinusUnit(x.get(1), t.get(1)));
				x = e;
			} else {
				programCounter.previous();
				break;
			}
		}
		return x;
	}
	
	private List<Expression> createTerm() throws SyntaxErrorException {
		List<Expression> x = createFactor();
		while (programCounter.hasNext()) {
			String str = programCounter.next();
			if (str.equals("*")) {
				List<Expression> t = createFactor();
				List<Expression> e = new ArrayList<Expression>();
				e.add(new TimeNumber(x.get(0), t.get(0)));
				e.add(new TimeUnit(x.get(1), t.get(1)));
				x = e;
			} else if (str.equals("/")) {
				List<Expression> t = createFactor();
				List<Expression> e = new ArrayList<Expression>();
				e.add(new DivideNumber(x.get(0), t.get(0)));
				e.add(new DivideUnit(x.get(1), t.get(1)));
				x = e;
			} else if (str.equals("^")) {
				List<Expression> t = createFactor();
				List<Expression> e = new ArrayList<Expression>();
				e.add(new PowNumber(x.get(0), t.get(0)));
				e.add(new PowUnit(x.get(1), t.get(0)));
				x = e;
			} else {
				programCounter.previous();
				break;
			}
		}
		return x;
	}
	
	private List<Expression> createUnit(List<Expression> arg0, String arg1) {
		String unit = arg1.replaceAll("(\\)\\[|\\])", "");
		List<String> parsedUnit = UnitParser.evaluatePrefix("1", unit);
		List<Expression> answer = new ArrayList<Expression>();
		answer.add(new TimeNumber(arg0.get(0), new Number(parsedUnit.get(0))));
		answer.add(new UnitFactor(arg0.get(1), new Unit(parsedUnit.get(1))));
		return answer;
	}
	
	private List<Expression> createFactor() throws SyntaxErrorException {
		String str = programCounter.next();
		if (str.matches(".*\\d.*")) {
			programCounter.previous();
			List<Expression> e = createNumber();
			return e;
		} else if (programCounter.hasNext()) {
			List<Expression> e = createExpression(); //再帰下がる
			// 下がった再帰から出てきたところ.ここで,はじめのカッコと閉じカッコの部分を確認する.
			String next = programCounter.next();
			if (str.equals("(")) {
				if (next.matches("^\\)\\[.*")) {
					e = createUnit(e, next);
				} else if (!next.equals(")")) {
					throw new SyntaxErrorException("Missing closing parenthesis.");
				}
			} else if (str.equals("sqrt(")) {
				ArrayList<Expression> t = new ArrayList<Expression>();
				t.add(new SqrtNumber(e.get(0)));
				t.add(new SqrtUnit(e.get(1)));
				e = t;
				if (next.matches("^\\)\\[.*")) {
					e = createUnit(e, next);
				} else if (!next.equals(")")) {
					throw new SyntaxErrorException("Missing closing parenthesis.");
				}
			} else {
				throw new SyntaxErrorException("Command not found : " + str.replaceAll("\\(", ""));
			} 
			return e;
		} else {
			throw new SyntaxErrorException("Missing right-hand side value.");
		}
	}
		
}
