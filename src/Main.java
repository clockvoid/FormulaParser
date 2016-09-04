import Exception.RuntimeErrorException;
import Exception.SyntaxErrorException;
import FormulaParser.Expression;
import FormulaParser.Parser;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Parser parser = new Parser("sqrt( 4 )[m^2] * 12");
			Expression exp = parser.compile();
			System.out.println(exp.evaluate());
		} catch (RuntimeErrorException e) {
			// TODO Auto-generated catch block
			System.out.println("RuntimeError : " + e.getMessage());
		} catch (SyntaxErrorException e) {
			// TODO Auto-generated catch block
			System.out.println("SyntaxError : " + e.getMessage());
		}
	}

}
