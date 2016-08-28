import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class Parser {
	
	private ArrayList<String> program;
	private ListIterator<String> programCounter;
	
	public Parser(String arg0) {
		this.program = new ArrayList<String>(Arrays.asList(arg0.split("\\s+")));
	}
	
	public void setProgram(String arg0) {
		this.program = new ArrayList<String>(Arrays.asList(arg0.split("\\s+")));
	}
	
	public Expression compile() {
		this.programCounter = program.listIterator();
		List<Expression> array = createExpression();
		Expression treeRoot = new TreeRoot(array.get(0), array.get(1));
		return treeRoot;
	}
	
	private List<Expression> createNumber() {
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
	
	private List<Expression> createExpression() {
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
	
	private List<Expression> createTerm() {
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
				e.add(new DevideNumber(x.get(0), t.get(0)));
				e.add(new DevideUnit(x.get(1), t.get(1)));
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
		answer.add(new Unit(unit));
		return answer;
	}
	
	private List<Expression> createFactor() {
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
					System.out.println("error");
				}
			} else if (str.equals("sqrt(")) {
				Expression t = new SqrtNumber(e.get(0));
				e.set(0, t);
				if (next.matches("^\\)\\[.*")) {
					e = createUnit(e, next);
				} else if (!next.equals(")")) {
					System.out.println("error!");
				}
			} else {
				System.out.println("command not found : " + str.replaceAll("\\(", ""));
			} 
			return e;
		} else {
			System.out.println("error");
			return null;
		}
	}
		
}
